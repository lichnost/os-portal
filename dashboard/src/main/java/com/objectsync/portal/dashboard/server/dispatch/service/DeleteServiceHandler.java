package com.objectsync.portal.dashboard.server.dispatch.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.rpc.shared.NoResult;
import com.gwtplatform.dispatch.shared.ActionException;
import com.objectsync.portal.core.PortalCore;
import com.objectsync.portal.dashboard.shared.dispatch.service.CreateServiceAction;
import com.objectsync.portal.dashboard.shared.dispatch.service.DeleteServiceAction;

public class DeleteServiceHandler extends
		AbstractActionHandler<DeleteServiceAction, NoResult> {

	private PortalCore _portalCore;
	private Provider<HttpServletRequest> _requestProvider;

	@Inject
	public DeleteServiceHandler(PortalCore portalCore,
			Provider<HttpServletRequest> requestProvider) {
		super(DeleteServiceAction.class);
		_portalCore = portalCore;
		_requestProvider = requestProvider;
	}

	@Override
	public NoResult execute(DeleteServiceAction action, ExecutionContext context)
			throws ActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo(DeleteServiceAction action, NoResult result,
			ExecutionContext context) throws ActionException {
		throw new ActionException("Undoing not supporeted");
	}

}
