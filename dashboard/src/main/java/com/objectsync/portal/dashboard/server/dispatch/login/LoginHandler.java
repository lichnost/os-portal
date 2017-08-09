package com.objectsync.portal.dashboard.server.dispatch.login;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.objectsync.portal.core.PortalCore;
import com.objectsync.portal.core.model.SecuredUser;
import com.objectsync.portal.core.model.User;
import com.objectsync.portal.dashboard.shared.dispatch.login.LoginAction;
import com.objectsync.portal.dashboard.shared.dispatch.login.LoginResult;

public class LoginHandler extends
		AbstractActionHandler<LoginAction, LoginResult> {

	private PortalCore _portalCore;
	private Provider<HttpServletRequest> _requestProvider;

	@Inject
	public LoginHandler(PortalCore portalCore,
			Provider<HttpServletRequest> requestProvider) {
		super(LoginAction.class);
		_portalCore = portalCore;
		_requestProvider = requestProvider;
	}

	@Override
	public LoginResult execute(LoginAction action, ExecutionContext context)
			throws ActionException {
		try {
			SecuredUser user = _portalCore.login(action.getEmail(),
					action.getPassword());
			User clientUser = new User();
			clientUser.setId(user.getId());
			clientUser.setEmail(user.getEmail());

			_requestProvider.get().getSession()
					.setAttribute(User.class.getName(), clientUser);

			return new LoginResult(clientUser);
		} catch (Exception e) {
			throw new ActionException(e.getMessage());
		}
	}

	@Override
	public void undo(LoginAction action, LoginResult result,
			ExecutionContext context) throws ActionException {
	}

}
