package com.objectsync.portal.dashboard.server.dispatch.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.rpc.shared.MultipleResult;
import com.gwtplatform.dispatch.shared.ActionException;
import com.objectsync.portal.core.PortalCore;
import com.objectsync.portal.core.model.WorkspaceServer;
import com.objectsync.portal.dashboard.shared.dispatch.service.GetUserServicesAction;

public class GetUserServicesHandler
		extends
		AbstractActionHandler<GetUserServicesAction, MultipleResult<WorkspaceServer>> {

	private PortalCore _portalCore;
	private Provider<HttpServletRequest> _requestProvider;

	@Inject
	public GetUserServicesHandler(PortalCore portalCore,
			Provider<HttpServletRequest> requestProvider) {
		super(GetUserServicesAction.class);
		_portalCore = portalCore;
		_requestProvider = requestProvider;
	}

	@Override
	public MultipleResult<WorkspaceServer> execute(GetUserServicesAction action,
			ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo(GetUserServicesAction action,
			MultipleResult<WorkspaceServer> result, ExecutionContext context)
			throws ActionException {
		throw new ActionException("Undoing not supporeted");
	}

}
