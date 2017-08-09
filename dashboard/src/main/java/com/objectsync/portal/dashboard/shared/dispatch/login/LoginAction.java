package com.objectsync.portal.dashboard.shared.dispatch.login;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class LoginAction extends UnsecuredActionImpl<LoginResult>{

	private String email;
	private String password;
	
	public LoginAction() {
	}
	
	public LoginAction(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
