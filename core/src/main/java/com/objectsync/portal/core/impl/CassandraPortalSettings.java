package com.objectsync.portal.core.impl;

public interface CassandraPortalSettings {

	public String getHost();
	
	public int getPort();
	
	public String getUsername();
	
	public String getPassword();
	
	public String getMainKeyspace();
	
}
