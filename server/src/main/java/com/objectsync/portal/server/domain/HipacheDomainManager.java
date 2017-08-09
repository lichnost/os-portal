package com.objectsync.portal.server.domain;

import java.util.List;

import javax.inject.Inject;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisException;
import com.lambdaworks.redis.RedisURI;

public class HipacheDomainManager implements DomainManager {

	private HipacheDomainSettings _settings;
	private RedisClient _redis;

	@Inject
	public HipacheDomainManager(HipacheDomainSettings settings) {
		_settings = settings;
		_redis = RedisClient.create(RedisURI.create("redis://"
				+ _settings.getHipacheRedisPassword() + "@"
				+ _settings.getHipacheRedisHost() + ":"
				+ _settings.getHipacheRedisPort()));
	}

	@Override
	public void register(String subdomain, List<String> urls) {
		try (RedisConnection<String, String> connection = connection()) {
			String key = frontendKey(subdomain);
			connection.rpush(key, subdomain);
			for (String u : urls) {
				connection.rpush(key, u);
			}
		}
	}

	private String frontendKey(String subdomain) {
		return "frontend:" + subdomain + "." + _settings.getMainDomain();
	}

	@Override
	public void unregister(String subdomain) {
		try (RedisConnection<String, String> connection = connection()) {
			String key = frontendKey(subdomain);
			connection.del(key);
		} catch (RedisException e) {
			throw new RuntimeException(e);
		}
	}

	private RedisConnection<String, String> connection() {
		return _redis.connect();
	}

}
