package com.vamekh.client.presenter;


import java.util.Date;

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
import com.vamekh.client.InstitutionServiceAsync;
import com.vamekh.client.event.EditInstitutionCancelledEvent;
import com.vamekh.client.event.InstitutionUpdatedEvent;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionTypeDTO;

public class EditInstitutionPresenter implements Presenter{

	public interface Display {
		
		HasSelectHandlers getSaveButton();

		HasSelectHandlers getCancelButton();

		HasValue<String> getCode();

		HasValue<String> getName();
		
		HasValue<String> getAddress();
		
		HasValue<String> getPhone();
		
		HasValue<String> getEmail();
		
		HasValue<String> getFax();
		
		InstitutionTypeDTO getType();
		
		void setType(InstitutionTypeDTO type);
		
		HasValue<Date> getRegDate();

		Widget asWidget();
		
	}
	
	private InstitutionServiceAsync rpcService;
	private HandlerManager eventBus;
	private Display display;
	private InstitutionDTO institutionDTO;
	
	public EditInstitutionPresenter(InstitutionServiceAsync rpcService, HandlerManager eventBus, Display display){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		institutionDTO = new InstitutionDTO();
		bind();
	}
	
	public EditInstitutionPresenter(InstitutionServiceAsync rpcService, HandlerManager eventBus, Display display, int id){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		bind();
		
		rpcService.getInstitution(id, new AsyncCallback<InstitutionDTO>() {
			
			public void onSuccess(InstitutionDTO result) {
				institutionDTO = result;
				EditInstitutionPresenter.this.display.getCode().setValue(institutionDTO.getCode());
				EditInstitutionPresenter.this.display.getName().setValue(institutionDTO.getName());
				EditInstitutionPresenter.this.display.getAddress().setValue(institutionDTO.getAddress());
				EditInstitutionPresenter.this.display.getPhone().setValue(institutionDTO.getPhone());
				EditInstitutionPresenter.this.display.getEmail().setValue(institutionDTO.getEmail());
				EditInstitutionPresenter.this.display.getFax().setValue(institutionDTO.getFax());
				EditInstitutionPresenter.this.display.setType(institutionDTO.getType());
				EditInstitutionPresenter.this.display.getRegDate().setValue(institutionDTO.getRegDate());
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving institution");
			}
		});
		
	}
	
	private void bind() {
		this.display.getSaveButton().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				if(checkFields()){
					rpcService.institutionCodeIsUnique(display.getCode().getValue(), institutionDTO.getId(), new AsyncCallback<Boolean>() {
	
						public void onFailure(Throwable caught) {
							Window.alert("Error checking code uniqueness");
						}
	
						public void onSuccess(Boolean result) {
							if(result){
								doSave();
							} else {
								Window.alert("Institution with this code is already registered");
							}
						}
					});
				}
			}
		});
		
		this.display.getCancelButton().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				eventBus.fireEvent(new EditInstitutionCancelledEvent());
			}
		});
	}
	
	private boolean checkFields() {
		String errorMessage = "";
		String codeField = display.getCode().getValue();
		String nameField = display.getCode().getValue();
		String emailField = display.getEmail().getValue();
		String addressField = display.getAddress().getValue();
		String phoneField = display.getPhone().getValue();
		String faxField = display.getFax().getValue();
		
		if(!codeField.matches("[A-Za-z0-9]+")){
			errorMessage += "Code contains invalid characters \n";
		}
		
		if(codeField == null || codeField.length() > 255 || codeField.length() == 0){
			errorMessage += "Code lenght must be between 1 and 255 \n";
		}
		
		if(codeField != null && !nameField.matches("[A-Za-z0-9 \\s]+")){
			errorMessage += "Name contains invalid characters \n";
		}
		
		if(nameField == null || nameField.length() > 255 || nameField.length() == 0){
			errorMessage += "Name length must be between 1 and 255 \n";
		}
		
		if(addressField != null && addressField.length() > 255){
			errorMessage += "Address length must be between 1 and 255 \n";
		}
		
		if(phoneField != null && !phoneField.matches("[0-9 \\s]+")){
			errorMessage += "Phone contains invalid characters \n";
		}
		
		if(phoneField != null && phoneField.length() > 255){
			errorMessage += "Phone length must be between 1 and 255 \n";
		}
		
		if(emailField != null && !emailField.matches("[A-Za-z0-9._%+-][A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{3}")){
			errorMessage += "Email contains invalid characters \n";
		}
		
		if(emailField != null && emailField.length() > 255){
			errorMessage += "Email length must be between 1 and 255 \n";
		}
		
		if(faxField != null && faxField.length() > 255){
			errorMessage += "Fax length must be between 1 and 255 \n";
		}
		
		if(!errorMessage.equals("")){
			Window.alert(errorMessage);
			return false;
		}
		
		return true;
	}
	
	private void doSave(){
		
		institutionDTO.setCode(display.getCode().getValue());
		institutionDTO.setName(display.getName().getValue());
		institutionDTO.setAddress(display.getAddress().getValue());
		institutionDTO.setPhone(display.getPhone().getValue());
		institutionDTO.setFax(display.getFax().getValue());
		institutionDTO.setEmail(display.getEmail().getValue());
		institutionDTO.setType(display.getType());
		institutionDTO.setRegDate(display.getRegDate().getValue());
		
		if(institutionDTO.getId() > 0){
			rpcService.updateInstitution(institutionDTO, new AsyncCallback<InstitutionDTO>() {
				
				public void onSuccess(InstitutionDTO result) {
					eventBus.fireEvent(new InstitutionUpdatedEvent(result));
				}
				
				public void onFailure(Throwable caught) {
					Window.alert("Error updating institution");
				}
			});
		} else {
			rpcService.addInstitution(institutionDTO, new AsyncCallback<InstitutionDTO>() {
				public void onSuccess(InstitutionDTO result) {
					eventBus.fireEvent(new InstitutionUpdatedEvent(result));
				}
				
				public void onFailure(Throwable caught) {
					Window.alert("Error adding institution");
				}
			});
		}
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
	
}
