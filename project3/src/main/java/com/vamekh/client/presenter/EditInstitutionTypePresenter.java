package com.vamekh.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.vamekh.client.InstitutionTypeServiceAsync;
import com.vamekh.client.event.EditInstitutionTypeCancelledEvent;
import com.vamekh.client.event.EditTemplateCancelledEvent;
import com.vamekh.client.event.InstitutionTypeUpdatedEvent;
import com.vamekh.client.event.TemplateUpdatedEvent;
import com.vamekh.shared.InstitutionTypeDTO;
import com.vamekh.shared.TemplateDTO;

public class EditInstitutionTypePresenter implements Presenter{

	public interface Display {
		HasSelectHandlers getSaveButton();

		HasSelectHandlers getCancelButton();

		HasValue<String> getCode();

		HasValue<String> getName();

		Widget asWidget();
	}
	
	private InstitutionTypeDTO typeDTO;
	private InstitutionTypeServiceAsync rpcService;
	private HandlerManager eventBus;
	private Display display;
	
	public EditInstitutionTypePresenter(InstitutionTypeServiceAsync rpcService, HandlerManager eventBus, Display display){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		typeDTO = new InstitutionTypeDTO();
		bind();
	}
	
	public EditInstitutionTypePresenter(InstitutionTypeServiceAsync rpcService, HandlerManager eventBus, Display display, int id){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		bind();
		
		rpcService.getInstitutionType(id, new AsyncCallback<InstitutionTypeDTO>() {
			
			public void onSuccess(InstitutionTypeDTO result) {
				typeDTO = result;
				EditInstitutionTypePresenter.this.display.getCode().setValue(typeDTO.getCode());
				EditInstitutionTypePresenter.this.display.getName().setValue(typeDTO.getName());
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving institution type");
			}
		});
	}
	
	private void bind() {
		
		this.display.getSaveButton().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				if(checkFields()){
					rpcService.isTypeUnique(display.getCode().getValue(), typeDTO.getId(), new AsyncCallback<Boolean>() {
		
						public void onFailure(Throwable caught) {
							Window.alert("Error checking code uniqueness");
						}
		
						public void onSuccess(Boolean result) {
							if(result){
								doSave();
							} else {
								Window.alert("Institution type with this code is already registered");
							}
						}
					});
				}
			}
		});
		
		this.display.getCancelButton().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				eventBus.fireEvent(new EditInstitutionTypeCancelledEvent());
			}
		});
		
	}

	private boolean checkFields() {
		String errorMessage = "";
		String codeField = display.getCode().getValue();
		String nameField = display.getName().getValue();
		
		if(codeField != null && !codeField.matches("[A-Za-z0-9]+")){
			errorMessage += "Code contains invalid characters \n";
		}
		
		if(codeField == null || codeField.length() > 20 || codeField.length() == 0){
			errorMessage += "Code lenght must be between 1 and 255 \n";
		}
		
		if(nameField != null && !nameField.matches("[A-Za-z0-9 \\s]+")){
			errorMessage += "Name contains invalid characters \n";
		}
		
		if(nameField == null || nameField.length() > 255 || nameField.length() == 0){
			errorMessage += "Name length must be between 1 and 255 \n";
		}
		
		if(!errorMessage.equals("")){
			Window.alert(errorMessage);
			return false;
		}
		
		return true;
	}
	
	private void doSave() {
	
		typeDTO.setCode(display.getCode().getValue());
		typeDTO.setName(display.getName().getValue());
		if(typeDTO.getId() > 0){
			rpcService.updateInstitutionType(typeDTO, new AsyncCallback<InstitutionTypeDTO>() {
				
				public void onSuccess(InstitutionTypeDTO result) {
					eventBus.fireEvent(new InstitutionTypeUpdatedEvent(result));
				}
				
				public void onFailure(Throwable caught) {
					Window.alert("Error updating institution type");
				}
			});
		} else {
			rpcService.addInstitutionType(typeDTO, new AsyncCallback<InstitutionTypeDTO>() {
				public void onSuccess(InstitutionTypeDTO result) {
					eventBus.fireEvent(new InstitutionTypeUpdatedEvent(result));
				}
				
				public void onFailure(Throwable caught) {
					Window.alert("Error adding institution type");
				}
			});
		}
	}


	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
	
	

}
