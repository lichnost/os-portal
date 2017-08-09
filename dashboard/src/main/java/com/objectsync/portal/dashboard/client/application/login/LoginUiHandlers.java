package com.objectsync.portal.dashboard.client.application.login;

import com.gwtplatform.mvp.client.UiHandlers;

interface LoginUiHandlers extends UiHandlers {

	void onLogin(String email, String password);

	void onRegister();
	
}
