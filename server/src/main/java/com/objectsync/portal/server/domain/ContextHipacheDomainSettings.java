package com.objectsync.portal.server.domain;

import com.objectsync.portal.server.ContextUtil;

public class ContextHipacheDomainSettings implements HipacheDomainSettings {

	@Override
	public String getHipacheRedisHost() {
		return ContextUtil
				.lookup("ObjectSync/server/domain/hipache-redis-host");
	}

	@Override
	public String getHipacheRedisPassword() {
		return ContextUtil
				.lookup("ObjectSync/server/domain/hipache-redis-password");
	}

	@Override
	public int getHipacheRedisPort() {
		return Integer
				.valueOf(ContextUtil
						.<String> lookup("ObjectSync/server/domain/hipache-redis-port"));
	}

	@Override
	public String getMainDomain() {
		return ContextUtil.lookup("ObjectSync/server/domain/domain");
	}

}
