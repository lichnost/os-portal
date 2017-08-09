package com.objectsync.portal.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkspaceServer implements Serializable {

	private String id;

	private String workspace;

	private SecuredUser user;

	private List<ServerOrigin> origins = new ArrayList<ServerOrigin>();

	public WorkspaceServer() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String domain) {
		this.workspace = domain;
	}

	public SecuredUser getUser() {
		return user;
	}

	public void setUser(SecuredUser user) {
		this.user = user;
	}

	public void addOrigin(ServerOrigin origin) {
		origins.add(origin);
	}

	public void addOrigins(List<ServerOrigin> origins) {
		this.origins.addAll(origins);
	}

	public void removeOrigin(ServerOrigin origin) {
		origins.remove(origin);
	}

	public List<ServerOrigin> getOrigins() {
		return Collections.unmodifiableList(origins);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkspaceServer other = (WorkspaceServer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
