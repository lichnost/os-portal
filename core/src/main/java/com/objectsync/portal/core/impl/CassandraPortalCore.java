package com.objectsync.portal.core.impl;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.objectsync.portal.core.PortalCore;
import com.objectsync.portal.core.RegistrationException;
import com.objectsync.portal.core.Util;
import com.objectsync.portal.core.model.SecuredUser;
import com.objectsync.portal.core.model.ServerOrigin;
import com.objectsync.portal.core.model.User;
import com.objectsync.portal.core.model.WorkspaceServer;

public class CassandraPortalCore implements PortalCore, Closeable {

	private CassandraPortalSettings _settings;
	private Cluster _cluster;
	private Session _session;

	@Inject
	public CassandraPortalCore(CassandraPortalSettings settings) {
		_settings = settings;
		init();
	}

	private void init() {
		Builder builder = Cluster.builder()
				.addContactPoint(_settings.getHost());
		if (_settings.getUsername() != null && _settings.getPassword() != null) {
			builder.withCredentials(_settings.getUsername(),
					_settings.getPassword());
		}
		if (_settings.getPort() > 0) {
			builder.withPort(_settings.getPort());
		}
		_cluster = builder.build();
		_session = _cluster.connect(_settings.getMainKeyspace());
	}

	private SecuredUser findUser(String email) {
		SecuredUser user = null;
		PreparedStatement stmt = _session
				.prepare("SELECT id, email, password FROM users WHERE email = :email");
		BoundStatement bst = new BoundStatement(stmt);
		bst.setString("email", email);

		ResultSet rs = _session.execute(bst);
		if (rs.iterator().hasNext()) {
			Row row = rs.iterator().next();
			user = new SecuredUser();
			user.setId(row.getUUID("id").toString());
			user.setEmail(row.getString("email"));
			user.setPassword(row.getString("password"));
		}

		return user;
	}

	@Override
	public SecuredUser register(String email, String password)
			throws RegistrationException {
		if (Util.isEmpty(email) || Util.isEmpty(password)) {
			throw new IllegalArgumentException(
					"Email and password should not be empty");
		}
		SecuredUser user = findUser(email);
		if (user != null) {
			throw new RegistrationException("User already registered");
		}
		PreparedStatement stmt = _session
				.prepare("INSERT INTO users (id, email, password) values (:id, :email, :password)");
		BoundStatement bst = new BoundStatement(stmt);
		UUID uuid = UUID.randomUUID();
		bst.setUUID("id", uuid);
		bst.setString("email", email);
		bst.setString("password", password);
		_session.execute(bst);

		user = new SecuredUser();
		user.setId(uuid.toString());
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}

	@Override
	public SecuredUser login(String email, String password) {
		if (Util.isEmpty(email) || Util.isEmpty(password)) {
			throw new IllegalArgumentException(
					"Email and password should not be empty");
		}

		SecuredUser user = findUser(email);
		return user;
	}

	private WorkspaceServer getWorkspaceServer(UUID id) {
		PreparedStatement stmt = _session
				.prepare("SELECT id, workspace FROM workspace_servers WHERE id = :id");
		BoundStatement bst = new BoundStatement(stmt);
		bst.setUUID("id", id);
		ResultSet rs = _session.execute(bst);
		if (rs.iterator().hasNext()) {
			Row row = rs.iterator().next();

			WorkspaceServer node = new WorkspaceServer();
			UUID nodeId = row.getUUID("id");
			node.setId(nodeId.toString());
			node.setWorkspace(row.getString("workspace"));
			node.addOrigins(getOrigins(nodeId));
			return node;
		}
		throw new RuntimeException("Not found");
	}

	private SecuredUser getUser(UUID id) {
		PreparedStatement stmt = _session
				.prepare("SELECT id, email, password FROM users WHERE id = :id");
		BoundStatement bst = new BoundStatement(stmt);
		bst.setUUID("id", id);
		ResultSet rs = _session.execute(bst);
		if (rs.iterator().hasNext()) {
			Row row = rs.iterator().next();

			SecuredUser user = new SecuredUser();
			user.setId(row.getUUID("id").toString());
			user.setEmail(row.getString("email"));
			user.setPassword(row.getString("password"));
			return user;
		}
		throw new RuntimeException("Not found");
	}

	private List<ServerOrigin> getOrigins(UUID serverId) {
		List<ServerOrigin> result = new ArrayList<ServerOrigin>();
		// TODO
		return result;
	}

	@Override
	public List<WorkspaceServer> getWorkspaceServers() {
		List<WorkspaceServer> result = new ArrayList<WorkspaceServer>();
		ResultSet rs = _session
				.execute("SELECT id, workspace FROM workspace_servers");
		for (Row serverRow : rs) {
			WorkspaceServer server = new WorkspaceServer();
			UUID serverId = serverRow.getUUID("id");
			server.setId(serverId.toString());
			server.setWorkspace(serverRow.getString("workspace"));
			server.addOrigins(getOrigins(serverId));

			// fetch user
			PreparedStatement userStmt = _session
					.prepare("SELECT id, user_id, workspace_server_id FROM user_servers WHERE workspace_server_id = :workspace_server_id");
			BoundStatement userBst = new BoundStatement(userStmt);
			userBst.setUUID("workspace_server_id", serverId);
			ResultSet userRs = _session.execute(userBst);
			Iterator<Row> userIter = userRs.iterator();
			Row userRow = userIter.next();
			server.setUser(getUser(userRow.getUUID("user_id")));

			result.add(server);
		}
		return result;
	}

	@Override
	public void saveWorkspaceServer(WorkspaceServer server) {
		UUID serverUuid = UUID.randomUUID();
		server.setId(serverUuid.toString());

		PreparedStatement workStmt = _session
				.prepare("INSERT INTO workspace_servers (id, workspace) values (:id, :workspace)");
		BoundStatement workBst = new BoundStatement(workStmt);
		workBst.setUUID("id", serverUuid);
		workBst.setString("workspace", server.getWorkspace());
		_session.execute(workBst);

		PreparedStatement userStmt = _session
				.prepare("INSERT INTO user_servers (id, user_id, workspace_server_id) values (:id, :user_id, :workspace_server_id)");
		BoundStatement userBst = new BoundStatement(userStmt);
		userBst.setUUID("id", UUID.randomUUID());
		userBst.setUUID("user_id", UUID.fromString(server.getUser().getId()));
		userBst.setUUID("workspace_server_id", serverUuid);
		_session.execute(userBst);

	}

	@Override
	public List<WorkspaceServer> getUserWorkspaceServers(User user) {
		List<WorkspaceServer> result = new ArrayList<WorkspaceServer>();

		SecuredUser securedUser = getUser(UUID.fromString(user.getId()));

		PreparedStatement workStmt = _session
				.prepare("SELECT id, user_id, workspace_server_id FROM user_servers WHERE user_id = :user_id");
		BoundStatement workBst = new BoundStatement(workStmt);
		workBst.setUUID("user_id", UUID.fromString(user.getId()));
		ResultSet rs = _session.execute(workBst);
		for (Row row : rs) {
			WorkspaceServer server = getWorkspaceServer(row
					.getUUID("workspace_server_id"));
			server.setUser(securedUser);

			result.add(server);
		}
		return result;
	}

	@Override
	public void close() throws IOException {
		_session.close();
		_cluster.close();
	}

}
