package com.objectsync.portal.dashboard.shared.dispatch.login;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.objectsync.portal.core.model.User;

public class LoginResult implements Result {

	private User user;

	public LoginResult() {
	}

	public LoginResult(User user) {
		this.user = user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
