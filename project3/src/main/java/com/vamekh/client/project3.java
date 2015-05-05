package com.vamekh.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class project3 implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		InstitutionTypeServiceAsync typeRpcService = GWT.create(InstitutionTypeService.class);
		InstitutionServiceAsync instRpcService = GWT.create(InstitutionService.class);
		ReturnServiceAsync returnRpcService = GWT.create(ReturnService.class);
		TemplateServiceAsync templateRpcService = GWT.create(TemplateService.class);
		HandlerManager eventBus = new HandlerManager(null);
		AppController appViewer = new AppController(typeRpcService, instRpcService, templateRpcService, returnRpcService, eventBus);
		appViewer.go(RootPanel.get());
	}
}
