package com.objectsync.portal.dashboard.shared.dispatch.register;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class RegisterAction extends UnsecuredActionImpl<RegisterResult>{

	private String email;
	private String password;
	
	public RegisterAction() {
	}
	
	public RegisterAction(String email, String password) {
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
