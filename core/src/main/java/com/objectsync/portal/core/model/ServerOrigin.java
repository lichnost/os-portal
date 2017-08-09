package com.objectsync.portal.core.model;

import java.io.Serializable;

public class ServerOrigin implements Serializable {

	private String id;

	private String origin;

	private String description;

	public ServerOrigin() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
