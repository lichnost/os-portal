package com.objectsync.portal.core.model;

import java.io.Serializable;

public class User implements Serializable {

	protected String id;
	
	protected String email;
	
	public User() {
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}
