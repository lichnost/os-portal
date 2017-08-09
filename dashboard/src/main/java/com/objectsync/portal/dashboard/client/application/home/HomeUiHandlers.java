package com.objectsync.portal.dashboard.client.application.home;

import com.gwtplatform.mvp.client.UiHandlers;
import com.objectsync.portal.core.model.WorkspaceServer;

public interface HomeUiHandlers extends UiHandlers {

	void onCreate();

	void onDelete(WorkspaceServer service);

}
