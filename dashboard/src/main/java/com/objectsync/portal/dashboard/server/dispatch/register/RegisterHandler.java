package com.objectsync.portal.dashboard.server.dispatch.register;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.objectsync.portal.core.PortalCore;
import com.objectsync.portal.core.model.SecuredUser;
import com.objectsync.portal.core.model.User;
import com.objectsync.portal.dashboard.shared.dispatch.register.RegisterAction;
import com.objectsync.portal.dashboard.shared.dispatch.register.RegisterResult;

public class RegisterHandler extends
		AbstractActionHandler<RegisterAction, RegisterResult> {

	private PortalCore _portalCore;
	private Provider<HttpServletRequest> _requestProvider;

	@Inject
	public RegisterHandler(PortalCore portalCore,
			Provider<HttpServletRequest> requestProvider) {
		super(RegisterAction.class);
		_portalCore = portalCore;
		_requestProvider = requestProvider;
	}

	@Override
	public RegisterResult execute(RegisterAction action,
			ExecutionContext context) throws ActionException {
		try {
			SecuredUser user = _portalCore.register(action.getEmail(),
					action.getPassword());
			User clientUser = new User();
			clientUser.setId(user.getId());
			clientUser.setEmail(user.getEmail());

			_requestProvider.get().getSession()
					.setAttribute(User.class.getName(), clientUser);

			return new RegisterResult(clientUser);
		} catch (Exception e) {
			throw new ActionException(e.getMessage());
		}
	}

	@Override
	public void undo(RegisterAction action, RegisterResult result,
			ExecutionContext context) throws ActionException {
	}

}
