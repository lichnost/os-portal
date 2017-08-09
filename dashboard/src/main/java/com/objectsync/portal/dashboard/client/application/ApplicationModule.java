package com.objectsync.portal.dashboard.client.application;


import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.objectsync.portal.dashboard.client.application.home.HomeModule;
import com.objectsync.portal.dashboard.client.application.login.LoginModule;
import com.objectsync.portal.dashboard.client.application.register.RegisterModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
        
        install(new LoginModule());
        install(new RegisterModule());
        install(new HomeModule());
    }
}
