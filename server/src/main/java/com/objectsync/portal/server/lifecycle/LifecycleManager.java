package com.objectsync.portal.server.lifecycle;

import com.objectsync.portal.core.model.WorkspaceServer;

public interface LifecycleManager {

	public enum State {
		STARTED, STOPED, REJECTED
	}

	public interface Handler {

		public State onStart(WorkspaceServer server);

		public State onStop(WorkspaceServer server);

	}

	void setHandler(Handler handler);
	
	String getHostName();
	
}
