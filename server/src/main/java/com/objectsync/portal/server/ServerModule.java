package com.objectsync.portal.server;

import com.google.inject.AbstractModule;
import com.objectsync.portal.server.domain.ContextHipacheDomainSettings;
import com.objectsync.portal.server.domain.DomainManager;
import com.objectsync.portal.server.domain.HipacheDomainManager;
import com.objectsync.portal.server.domain.HipacheDomainSettings;
import com.objectsync.portal.server.lifecycle.ContextHazelcastLifecycleSettings;
import com.objectsync.portal.server.lifecycle.HazelcastLifecycleManager;
import com.objectsync.portal.server.lifecycle.HazelcastLifecycleSettings;
import com.objectsync.portal.server.lifecycle.LifecycleManager;
import com.objectsync.portal.server.workspace.CassandraWorkspaceManager;
import com.objectsync.portal.server.workspace.CassandraWorkspaceSettings;
import com.objectsync.portal.server.workspace.ContextCassandraWorkspaceSettings;
import com.objectsync.portal.server.workspace.WorkspaceManager;

public class ServerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(HipacheDomainSettings.class)
				.to(ContextHipacheDomainSettings.class);
		bind(DomainManager.class).to(HipacheDomainManager.class);

		bind(HazelcastLifecycleSettings.class).to(
				ContextHazelcastLifecycleSettings.class);
		bind(LifecycleManager.class).to(HazelcastLifecycleManager.class);

		bind(CassandraWorkspaceSettings.class).to(
				ContextCassandraWorkspaceSettings.class);
		bind(WorkspaceManager.class).to(CassandraWorkspaceManager.class);

	}

}
