package com.objectsync.portal.server.lifecycle;

import com.objectsync.portal.server.ContextUtil;

public class ContextHazelcastLifecycleSettings implements HazelcastLifecycleSettings {

	@Override
	public String getGroupName() {
		return ContextUtil.lookup("ObjectSync/server/lifecycle/hazelcast-group-name");
	}

	@Override
	public String getGroupPassword() {
		return ContextUtil.lookup("ObjectSync/server/lifecycle/hazelcast-group-password");
	}

}
