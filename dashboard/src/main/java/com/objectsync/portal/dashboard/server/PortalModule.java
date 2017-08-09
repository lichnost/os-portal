package com.objectsync.portal.dashboard.server;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.objectsync.portal.core.PortalCore;
import com.objectsync.portal.core.impl.CassandraPortalCore;
import com.objectsync.portal.core.impl.CassandraPortalSettings;

public class PortalModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CassandraPortalSettings.class).to(ContextCassandraPortalCoreSettings.class).asEagerSingleton();
		bind(PortalCore.class).to(CassandraPortalCore.class)
				.in(Singleton.class);
	}

}
