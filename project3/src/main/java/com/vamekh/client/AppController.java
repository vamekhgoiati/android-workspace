package com.vamekh.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.vamekh.client.event.AddInstitutionEvent;
import com.vamekh.client.event.AddInstitutionEventHandler;
import com.vamekh.client.event.AddInstitutionTypeEvent;
import com.vamekh.client.event.AddInstitutionTypeEventHandler;
import com.vamekh.client.event.AddReturnEvent;
import com.vamekh.client.event.AddReturnEventHandler;
import com.vamekh.client.event.AddTemplateEvent;
import com.vamekh.client.event.AddTemplateEventHandler;
import com.vamekh.client.event.EditInstitutionCancelledEvent;
import com.vamekh.client.event.EditInstitutionCancelledEventHandler;
import com.vamekh.client.event.EditInstitutionEvent;
import com.vamekh.client.event.EditInstitutionEventHandler;
import com.vamekh.client.event.EditInstitutionTypeCancelledEvent;
import com.vamekh.client.event.EditInstitutionTypeCancelledEventHandler;
import com.vamekh.client.event.EditInstitutionTypeEvent;
import com.vamekh.client.event.EditInstitutionTypeEventHandler;
import com.vamekh.client.event.EditReturnCancelledEvent;
import com.vamekh.client.event.EditReturnCancelledEventHandler;
import com.vamekh.client.event.EditReturnEvent;
import com.vamekh.client.event.EditReturnEventHandler;
import com.vamekh.client.event.EditTemplateCancelledEvent;
import com.vamekh.client.event.EditTemplateCancelledEventHandler;
import com.vamekh.client.event.EditTemplateEvent;
import com.vamekh.client.event.EditTemplateEventHandler;
import com.vamekh.client.event.InstitutionTypeUpdatedEvent;
import com.vamekh.client.event.InstitutionTypeUpdatedEventHandler;
import com.vamekh.client.event.InstitutionUpdatedEvent;
import com.vamekh.client.event.InstitutionUpdatedEventHandler;
import com.vamekh.client.event.ReturnUpdatedEvent;
import com.vamekh.client.event.ReturnUpdatedEventHandler;
import com.vamekh.client.event.TemplateUpdatedEvent;
import com.vamekh.client.event.TemplateUpdatedEventHandler;
import com.vamekh.client.presenter.EditInstitutionPresenter;
import com.vamekh.client.presenter.EditInstitutionTypePresenter;
import com.vamekh.client.presenter.EditReturnPresenter;
import com.vamekh.client.presenter.EditTemplatePresenter;
import com.vamekh.client.presenter.MainPresenter;
import com.vamekh.client.presenter.Presenter;
import com.vamekh.client.view.EditInstitutionTypeView;
import com.vamekh.client.view.EditInstitutionView;
import com.vamekh.client.view.EditReturnView;
import com.vamekh.client.view.EditTemplateView;
import com.vamekh.client.view.MainView;

public class AppController implements Presenter, ValueChangeHandler<String>{

	private InstitutionTypeServiceAsync typeRpcService;
	private InstitutionServiceAsync instRpcService;
	private TemplateServiceAsync templateRpcService;
	private ReturnServiceAsync returnRpcService;
	private HandlerManager eventBus;
	private HasWidgets container;
	
	public AppController(InstitutionTypeServiceAsync typeRpcService, InstitutionServiceAsync instRpcService, TemplateServiceAsync templateRpcService, ReturnServiceAsync returnRpcService, 
			HandlerManager eventBus) {
		
		this.typeRpcService = typeRpcService;
		this.instRpcService = instRpcService;
		this.templateRpcService = templateRpcService;
		this.returnRpcService = returnRpcService;
		this.eventBus = eventBus;
		
		bind();
	}
	
	private void bind() {
		
		History.addValueChangeHandler(this);
		
		eventBus.addHandler(AddInstitutionTypeEvent.TYPE,
				new AddInstitutionTypeEventHandler() {
					
					public void onAddInstitutionType(AddInstitutionTypeEvent event) {
						doAddNewInsitutionType();
					}
				});
				
		eventBus.addHandler(EditInstitutionTypeEvent.TYPE,
				new EditInstitutionTypeEventHandler() {
					
					public void onEditInstitutionType(EditInstitutionTypeEvent event) {
						doEditInstitutionType(event.getId());
					}
				});
		
		eventBus.addHandler(EditInstitutionTypeCancelledEvent.TYPE,
				new EditInstitutionTypeCancelledEventHandler() {
					
					public void onEditInstitutionTypeCancelled(
							EditInstitutionTypeCancelledEvent event) {
						doEditInstitutionTypeCancelled();
					}
				});
		
		eventBus.addHandler(InstitutionTypeUpdatedEvent.TYPE,
				new InstitutionTypeUpdatedEventHandler() {
					
					public void onInstitutionTypeUpdated(InstitutionTypeUpdatedEvent event) {
						doInstitutionTypeUpdated();
					}
				});
		
		eventBus.addHandler(AddInstitutionEvent.TYPE,
				new AddInstitutionEventHandler() {
					
					public void onAddInstitution(AddInstitutionEvent event) {
						doAddNewInsitution();
					}
				});
				
		eventBus.addHandler(EditInstitutionEvent.TYPE,
				new EditInstitutionEventHandler() {
					
					public void onEditInstitution(EditInstitutionEvent event) {
						doEditInstitution(event.getId());
					}
				});
		
		eventBus.addHandler(EditInstitutionCancelledEvent.TYPE,
				new EditInstitutionCancelledEventHandler() {
					public void onEditInstitutionCancelled(
							EditInstitutionCancelledEvent event) {
						doEditInstitutionCancelled();
					}
				});
		
		eventBus.addHandler(InstitutionUpdatedEvent.TYPE,
				new InstitutionUpdatedEventHandler() {
					public void onInstitutionUpdated(
							InstitutionUpdatedEvent event) {
						doInstitutionUpdated();
					}
				});
		
		eventBus.addHandler(AddTemplateEvent.TYPE,
				new AddTemplateEventHandler() {

					public void onAddTemplate(AddTemplateEvent event) {
						doAddNewTemplate();
					}
				});

		eventBus.addHandler(EditTemplateEvent.TYPE,
				new EditTemplateEventHandler() {

					public void onEditTemplate(EditTemplateEvent event) {
						doEditTemplate(event.getId());
					}
				});

		eventBus.addHandler(EditTemplateCancelledEvent.TYPE,
				new EditTemplateCancelledEventHandler() {

					public void onEditTemplateCancelled(
							EditTemplateCancelledEvent event) {
						doEditTemplateCancelled();
					}
				});

		eventBus.addHandler(TemplateUpdatedEvent.TYPE,
				new TemplateUpdatedEventHandler() {

					public void onTemplateUpdated(TemplateUpdatedEvent event) {
						doTemplateUpdated();
					}
				});
		
		eventBus.addHandler(AddReturnEvent.TYPE,
				new AddReturnEventHandler() {
					
					public void onAddReturn(AddReturnEvent event) {
						doAddNewReturn();
					}
				});

		eventBus.addHandler(EditReturnEvent.TYPE,
				new EditReturnEventHandler() {
					
					public void onEditReturn(EditReturnEvent event) {
						doEditReturn(event.getId());
					}
				});
		
		eventBus.addHandler(EditReturnCancelledEvent.TYPE,
				new EditReturnCancelledEventHandler() {
					
					public void onEditReturnCancelled(EditReturnCancelledEvent event) {
						doEditReturnCancelled();
					}
				});
		
		eventBus.addHandler(ReturnUpdatedEvent.TYPE,
				new ReturnUpdatedEventHandler() {
					
					public void onReturnUpdated(ReturnUpdatedEvent event) {
						doReturnUpdated();
					}
				});
		
	}

	private void doInstitutionTypeUpdated() {
		History.newItem("listType");
	}

	private void doEditInstitutionTypeCancelled() {
		History.newItem("listType");
	}

	private void doEditInstitutionType(int id) {
		History.newItem("editType", false);
		Presenter presenter = new EditInstitutionTypePresenter(typeRpcService, eventBus,
				new EditInstitutionTypeView(), id);
		presenter.go(container);
	}

	private void doAddNewInsitutionType() {
		History.newItem("addInstitutionType");
	}
	
	private void doInstitutionUpdated() {
		History.newItem("listInstitution");
	}

	private void doEditInstitutionCancelled() {
		History.newItem("listInstitution");
	}

	private void doEditInstitution(int id) {
		History.newItem("editInstitution", false);
		Presenter presenter = new EditInstitutionPresenter(instRpcService, eventBus,
				new EditInstitutionView(), id);
		presenter.go(container);
	}

	private void doAddNewInsitution() {
		History.newItem("addInstitution");
	}
	
	private void doTemplateUpdated() {
		History.newItem("listTemplate");
	}

	private void doEditTemplateCancelled() {
		History.newItem("listTemplate");
	}

	private void doEditTemplate(int id) {
		History.newItem("editTemplate", false);
		Presenter presenter = new EditTemplatePresenter(templateRpcService, eventBus,
				new EditTemplateView(), id);
		presenter.go(container);
	}

	private void doAddNewTemplate() {
		History.newItem("addTemplate");
	}
	
	private void doReturnUpdated() {
		History.newItem("listReturn");
	}

	private void doEditReturnCancelled() {
		History.newItem("listReturn");
	}

	private void doEditReturn(int id) {
		History.newItem("editReturn", false);
		Presenter presenter = new EditReturnPresenter(returnRpcService, eventBus,
				new EditReturnView(), id);
		presenter.go(container);
	}

	private void doAddNewReturn() {
		History.newItem("addReturn");
	}
	
	public void onValueChange(ValueChangeEvent<String> event) {
		
		String token = event.getValue();
		
		if (token != null) {
			Presenter presenter = null;

			if (token.equals("list")) {
				presenter = new MainPresenter(typeRpcService, instRpcService, templateRpcService, returnRpcService, eventBus,
						new MainView());
			}
			
			if (token.equals("listType")) {
				presenter = new MainPresenter(typeRpcService, instRpcService, templateRpcService, returnRpcService, eventBus,
						new MainView());
			}
			
			if (token.equals("listInstitution")) {
				presenter = new MainPresenter(typeRpcService, instRpcService, templateRpcService, returnRpcService, eventBus,
						new MainView(1));
			}
			
			if (token.equals("listTemplate")) {
				presenter = new MainPresenter(typeRpcService, instRpcService, templateRpcService, returnRpcService, eventBus,
						new MainView(2));
			}
			
			if (token.equals("listReturn")) {
				presenter = new MainPresenter(typeRpcService, instRpcService, templateRpcService, returnRpcService, eventBus,
						new MainView(3));
			}
			
			if (token.equals("addInstitutionType")) {
				presenter = new EditInstitutionTypePresenter(typeRpcService, eventBus, new EditInstitutionTypeView());	
			}
			
			if (token.equals("addInstitution")) {
				presenter = new EditInstitutionPresenter(instRpcService, eventBus, new EditInstitutionView());	
			}
			
			if (token.equals("editType")) {
				presenter = new EditInstitutionTypePresenter(typeRpcService, eventBus, new EditInstitutionTypeView());	
			}
			
			if (token.equals("editInstitution")) {
				presenter = new EditInstitutionPresenter(instRpcService, eventBus, new EditInstitutionView());	
			}
			
			if (token.equals("addTemplate")) {
				presenter = new EditTemplatePresenter(templateRpcService, eventBus, new EditTemplateView());
			}
			
			if (token.equals("editTemplate")) {
				presenter = new EditTemplatePresenter(templateRpcService, eventBus, new EditTemplateView());
			}
			
			if (token.equals("addReturn")) {
				presenter = new EditReturnPresenter(returnRpcService, eventBus, new EditReturnView());
			}
			
			if (token.equals("editReturn")) {
				presenter = new EditReturnPresenter(returnRpcService, eventBus, new EditReturnView());
			}

			if (presenter != null) {
				presenter.go(container);
			}
		}
		
	}

	public void go(HasWidgets container) {
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("list");
		} else {
			History.fireCurrentHistoryState();
		}
	}

}
