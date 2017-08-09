package com.objectsync.portal.dashboard.shared.dispatch.register;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.objectsync.portal.core.model.User;

public class RegisterResult implements Result {

	private User user;

	public RegisterResult() {
	}

	public RegisterResult(User user) {
		this.user = user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
