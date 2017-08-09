package com.objectsync.portal.dashboard.client.application.login;

import gwt.material.design.client.ui.MaterialTextBox;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class LoginView extends ViewWithUiHandlers<LoginPresenter> implements
		LoginPresenter.MyView {
	interface Binder extends UiBinder<Widget, LoginView> {
	}

	@UiField
	MaterialTextBox email;

	@UiField
	MaterialTextBox password;

	@Inject
	LoginView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("loginButton")
	void onLogin(ClickEvent event) {
		getUiHandlers().onLogin(email.getValue(), password.getValue());
	}
	
	@UiHandler("registerButton")
	void onRegister(ClickEvent event) {
		getUiHandlers().onRegister();
	}

}
