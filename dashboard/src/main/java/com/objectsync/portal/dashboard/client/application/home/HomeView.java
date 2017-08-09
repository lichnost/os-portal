package com.objectsync.portal.dashboard.client.application.home;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.objectsync.portal.core.model.WorkspaceServer;

public class HomeView extends ViewWithUiHandlers<HomePresenter> implements
		HomePresenter.MyView {
	interface Binder extends UiBinder<Widget, HomeView> {
	}

	@UiField
	SimplePanel serversPanel;
	DataGrid<WorkspaceServer> grid;
	ProvidesKey<WorkspaceServer> keyProvider = new ProvidesKey<WorkspaceServer>() {

		@Override
		public Object getKey(WorkspaceServer paramT) {
			return paramT.getId();
		}
	};
	SingleSelectionModel<WorkspaceServer> sm;

	@Inject
	HomeView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
		initGrid();
	}

	private void initGrid() {
		grid = new DataGrid<WorkspaceServer>();
		TextColumn<WorkspaceServer> domain = new TextColumn<WorkspaceServer>() {
			@Override
			public String getValue(WorkspaceServer object) {
				return object.getWorkspace();
			}
		};
		grid.addColumn(domain);
		grid.setColumnWidth(domain, "100%");
		sm = new SingleSelectionModel<WorkspaceServer>(keyProvider);
		sm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				onServiceSelectionChanged();
			}
		});
		grid.setSelectionModel(sm);
	}

	private void onServiceSelectionChanged() {

	}

	@UiHandler("createButton")
	void onLogin(ClickEvent event) {
		getUiHandlers().onCreate();
	}

	@UiHandler("deleteButton")
	void onDelete(ClickEvent event) {
		WorkspaceServer selected = sm.getSelectedObject();
		if (selected != null) {
			getUiHandlers().onDelete(selected);
		}
	}

}
