package com.objectsync.portal.dashboard.client.application.register;

import gwt.material.design.client.ui.MaterialTextBox;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.objectsync.portal.dashboard.shared.Util;

public class RegisterView extends ViewWithUiHandlers<RegisterPresenter> implements
		RegisterPresenter.MyView {
	interface Binder extends UiBinder<Widget, RegisterView> {
	}

	@UiField
	MaterialTextBox email;

	@UiField
	MaterialTextBox password;
	
	@UiField
	MaterialTextBox passwordRepeat;

	@Inject
	RegisterView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("loginButton")
	void onLogin(ClickEvent event) {
		getUiHandlers().onLogin();
	}
	
	@UiHandler("registerButton")
	void onRegister(ClickEvent event) {
		boolean valid = true;
		if	(Util.isEmpty(email.getValue())) {
			email.setError("Email should not be empty");
			valid = false;
		}
		if	(Util.isEmpty(password.getValue())) {
			password.setError("Password should not be empty");
			valid = false;
		}
		if	(!password.getValue().equals(passwordRepeat.getValue())) {
			password.setError("Passwords sould match");
			passwordRepeat.setError("Passwords sould match");
			valid = false;
		}
		
		if (valid) {
			getUiHandlers().onRegister(email.getValue(), password.getValue());
		}
	}

}
