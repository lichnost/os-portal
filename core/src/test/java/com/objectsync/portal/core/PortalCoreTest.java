package com.objectsync.portal.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;
import com.objectsync.portal.core.impl.CassandraPortalCore;
import com.objectsync.portal.core.impl.CassandraPortalSettings;
import com.objectsync.portal.core.model.SecuredUser;
import com.objectsync.portal.core.model.WorkspaceServer;

public class PortalCoreTest {

	CassandraPortalSettings settings;

	@Before
	public void before() throws ConfigurationException, TTransportException,
			IOException, InterruptedException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra(60000);
		settings = new CassandraPortalSettings() {

			@Override
			public String getUsername() {
				return null;
			}

			@Override
			public int getPort() {
				return EmbeddedCassandraServerHelper.getNativeTransportPort();
			}

			@Override
			public String getPassword() {
				return null;
			}

			@Override
			public String getMainKeyspace() {
				return "objectsync_service";
			}

			@Override
			public String getHost() {
				return EmbeddedCassandraServerHelper.getHost();
			}
		};

		Builder builder = Cluster.builder().addContactPoint(settings.getHost());
		builder.withPort(settings.getPort());
		Cluster cluster = builder.build();
		Session session = cluster.connect();

		BufferedReader r = new BufferedReader(new InputStreamReader(
				CassandraPortalCore.class.getResourceAsStream("init.sql")));
		String l;
		while ((l = r.readLine()) != null) {
			if (!l.trim().isEmpty()) {
				session.execute(l);
				if (l.contains("KEYSPACE")) {
					session.execute("USE " + settings.getMainKeyspace() + ";");
				}
			}
		}
		r.close();

		session.close();
		cluster.close();
	}

	@Test
	public void test() throws IOException {
		CassandraPortalCore portal = new CassandraPortalCore(settings);
		SecuredUser user1 = portal.register("user1@example.com", "password");
		SecuredUser user1login = portal.login("user1@example.com", "password");

		Assert.assertEquals(user1.getId(), user1login.getId());
		Assert.assertEquals(user1.getEmail(), user1login.getEmail());

		WorkspaceServer server1 = new WorkspaceServer();
		server1.setUser(user1);
		server1.setWorkspace("workspace1");
		portal.saveWorkspaceServer(server1);

		List<WorkspaceServer> userServers = portal
				.getUserWorkspaceServers(user1login);
		Assert.assertEquals(1, userServers.size());

		List<WorkspaceServer> allServers = portal.getWorkspaceServers();
		Assert.assertEquals(1, allServers.size());

		portal.close();
	}

	@After
	public void after() {
		//
	}

}
