package com.objectsync.portal.dashboard.client.application.home;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.objectsync.portal.core.model.WorkspaceServer;
import com.objectsync.portal.dashboard.client.application.ApplicationPresenter;
import com.objectsync.portal.dashboard.client.application.Session;
import com.objectsync.portal.dashboard.client.place.NameTokens;

public class HomePresenter extends
		Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements
		HomeUiHandlers {
	interface MyView extends View, HasUiHandlers<HomePresenter> {
	}

	@ProxyStandard
	@NameToken(NameTokens.HOME)
	interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	Session _session;
	PlaceManager _placeManager;
	DispatchAsync _dispatcher;

	@Inject
	HomePresenter(EventBus eventBus, MyView view, MyProxy proxy,
			Session session, PlaceManager placeManager, DispatchAsync dispatcher) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

		_session = session;
		_placeManager = placeManager;
		_dispatcher = dispatcher;
		getView().setUiHandlers(this);
	}

	@Override
	public void onCreate() {
		PlaceRequest req = new PlaceRequest.Builder().nameToken(
				NameTokens.SERVICE_CREATE).build();
		_placeManager.revealPlace(req);
	}

	@Override
	public void onDelete(WorkspaceServer service) {
		// TODO Auto-generated method stub

	}
}
