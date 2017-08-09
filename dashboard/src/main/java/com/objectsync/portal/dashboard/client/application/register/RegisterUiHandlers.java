package com.objectsync.portal.dashboard.client.application.register;

import com.gwtplatform.mvp.client.UiHandlers;

interface RegisterUiHandlers extends UiHandlers {

	void onLogin();

	void onRegister(String email, String password);
	
}
