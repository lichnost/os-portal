package com.objectsync.portal.server.domain;

import java.util.List;

public interface DomainManager {

	void register(String subdomain, List<String> urls);
	
	void unregister(String subdomain);
	
}
