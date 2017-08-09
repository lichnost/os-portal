package com.objectsync.portal.dashboard.client.gin;

import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.objectsync.portal.dashboard.client.application.ApplicationModule;
import com.objectsync.portal.dashboard.client.application.Session;
import com.objectsync.portal.dashboard.client.place.NameTokens;
import com.objectsync.portal.dashboard.client.resources.ResourceLoader;

public class ClientModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		install(new DefaultModule());
		install(new RpcDispatchAsyncModule.Builder().build());
		install(new ApplicationModule());

		bind(ResourceLoader.class).asEagerSingleton();
		bind(Session.class).asEagerSingleton();

		// DefaultPlaceManager Places
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.LOGIN);
		bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.LOGIN);
		bindConstant().annotatedWith(UnauthorizedPlace.class).to(
				NameTokens.LOGIN);
	}
}
