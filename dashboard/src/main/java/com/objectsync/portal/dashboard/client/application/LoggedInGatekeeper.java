package com.objectsync.portal.dashboard.client.application;

import javax.inject.Inject;

import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

@DefaultGatekeeper
public class LoggedInGatekeeper implements Gatekeeper {

	private Session _session;

	@Inject
	LoggedInGatekeeper(Session session) {
		_session = session;
	}

	@Override
	public boolean canReveal() {
		return _session.isLoggedIn();
	}

}
