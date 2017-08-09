package com.objectsync.portal.server.workspace;

import javax.inject.Singleton;

import com.objectsync.portal.server.ContextUtil;

@Singleton
public class ContextCassandraWorkspaceSettings implements CassandraWorkspaceSettings {

	@Override
	public String getHost() {
		return ContextUtil.lookup("ObjectSync/server/workspace/cassandra-host");
	}

	@Override
	public int getPort() {
		return Integer.valueOf(ContextUtil.<String>lookup("ObjectSync/server/workspace/cassandra-port"));
	}

	@Override
	public String getUsername() {
		return ContextUtil.lookup("ObjectSync/server/workspace/cassandra-username");
	}

	@Override
	public String getPassword() {
		return ContextUtil.lookup("ObjectSync/server/workspace/cassandra-password");
	}

}
