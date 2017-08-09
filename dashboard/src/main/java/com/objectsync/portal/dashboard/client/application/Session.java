package com.objectsync.portal.dashboard.client.application;

import com.objectsync.portal.core.model.User;

public class Session {

	private boolean _loggedIn = false;
	private User _user;

	public Session() {

	}

	public boolean isLoggedIn() {
		return _loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		_loggedIn = loggedIn;
	}
	
	public void setUser(User _user) {
		this._user = _user;
	}

	public User getUser() {
		return _user;
	}
	
}
