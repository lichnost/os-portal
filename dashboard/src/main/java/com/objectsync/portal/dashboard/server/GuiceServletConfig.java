package com.objectsync.portal.dashboard.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.objectsync.portal.dashboard.server.dispatch.DispatchServletModule;
import com.objectsync.portal.dashboard.server.dispatch.ServerModule;

public class GuiceServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new PortalModule(), new ServerModule(),
				new DispatchServletModule());
	}

}
