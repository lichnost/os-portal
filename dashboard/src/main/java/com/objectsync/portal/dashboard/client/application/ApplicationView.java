package com.objectsync.portal.dashboard.client.application;

import gwt.material.design.client.ui.MaterialNavBar;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ApplicationView extends ViewImpl implements
		ApplicationPresenter.MyView {

	interface Binder extends UiBinder<Widget, ApplicationView> {
	}

	@UiField
	MaterialNavBar navBar;

	@UiField
	HTMLPanel mainContainer;

	@Inject
	ApplicationView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));

		bindSlot(ApplicationPresenter.SLOT_MAIN, mainContainer);
	}

}
