package com.objectsync.portal.dashboard.client.application.login;

import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.objectsync.portal.dashboard.client.application.ApplicationPresenter;
import com.objectsync.portal.dashboard.client.application.Session;
import com.objectsync.portal.dashboard.client.place.NameTokens;
import com.objectsync.portal.dashboard.shared.dispatch.login.LoginAction;
import com.objectsync.portal.dashboard.shared.dispatch.login.LoginResult;

public class LoginPresenter extends
		Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> implements
		LoginUiHandlers {
	interface MyView extends View, HasUiHandlers<LoginPresenter> {
	}

	@ProxyStandard
	@NameToken(NameTokens.LOGIN)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	Session _session;
	PlaceManager _placeManager;
	DispatchAsync _dispatcher;

	@Inject
	LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			Session session, PlaceManager placeManager, DispatchAsync dispatcher) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

		_session = session;
		_placeManager = placeManager;
		_dispatcher = dispatcher;
		getView().setUiHandlers(this);
	}

	@Override
	public void onLogin(String email, String password) {
		_dispatcher.execute(new LoginAction(email, password),
				new AsyncCallback<LoginResult>() {
					@Override
					public void onFailure(Throwable caught) {
						//TODO error
						MaterialToast.fireToast(caught.getMessage());
					}

					@Override
					public void onSuccess(LoginResult result) {
						_session.setUser(result.getUser());
						_session.setLoggedIn(true);
						PlaceRequest req = new PlaceRequest.Builder()
								.nameToken(NameTokens.HOME).build();
						_placeManager.revealPlace(req);
					}
				});
	}

	@Override
	public void onRegister() {
		PlaceRequest req = new PlaceRequest.Builder().nameToken(
				NameTokens.REGISTER).build();
		_placeManager.revealPlace(req);
	}
}
