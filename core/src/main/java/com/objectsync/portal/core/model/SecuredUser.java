package com.objectsync.portal.core.model;


public class SecuredUser extends User implements Secured {

	protected String password;

	public SecuredUser() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public User insecure() {
		User user = new User();
		user.email = email;
		return user;
	}

}
