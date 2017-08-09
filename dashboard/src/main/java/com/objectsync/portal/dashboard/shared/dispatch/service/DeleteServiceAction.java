package com.objectsync.portal.dashboard.shared.dispatch.service;

import com.gwtplatform.dispatch.rpc.shared.NoResult;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;
import com.objectsync.portal.core.model.WorkspaceServer;

public class DeleteServiceAction extends UnsecuredActionImpl<NoResult> {

	private WorkspaceServer service;

	public DeleteServiceAction(WorkspaceServer service) {
		this.service = service;
	}

	public WorkspaceServer getService() {
		return service;
	}

}
