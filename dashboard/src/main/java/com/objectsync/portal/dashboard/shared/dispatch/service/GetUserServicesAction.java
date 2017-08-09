package com.objectsync.portal.dashboard.shared.dispatch.service;

import com.gwtplatform.dispatch.rpc.shared.MultipleResult;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;
import com.objectsync.portal.core.model.User;
import com.objectsync.portal.core.model.WorkspaceServer;

public class GetUserServicesAction extends
		UnsecuredActionImpl<MultipleResult<WorkspaceServer>> {

	private User user;

	public GetUserServicesAction(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
