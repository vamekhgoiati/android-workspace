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
import com.vamekh.client.ReturnServiceAsync;
import com.vamekh.client.event.EditReturnCancelledEvent;
import com.vamekh.client.event.ReturnUpdatedEvent;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.ReturnDTO;
import com.vamekh.shared.TemplateDTO;

public class EditReturnPresenter implements Presenter{
	
	public interface Display{
		
		HasSelectHandlers getSaveButton();

		HasSelectHandlers getCancelButton();
		
		HasValue<String> getCode();

		HasValue<String> getDescription();
		
		InstitutionDTO getInstitution();
		
		void setInstitution(InstitutionDTO inst);
		
		TemplateDTO getTemplate();
		
		void setTemplate(TemplateDTO template);
		
		Widget asWidget();
		
	}
	
	private ReturnServiceAsync rpcService;
	private HandlerManager eventBus;
	private Display display;
	private ReturnDTO returnDTO;
	
	public EditReturnPresenter(ReturnServiceAsync rpcService, HandlerManager eventBus, Display display){
		
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		returnDTO = new ReturnDTO();
		bind();
		
	}
	
	public EditReturnPresenter(ReturnServiceAsync rpcService, HandlerManager eventBus, Display display, int id){
		
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		bind();
		
		rpcService.getReturn(id, new AsyncCallback<ReturnDTO>() {
			
			public void onSuccess(ReturnDTO result) {
				returnDTO = result;
				EditReturnPresenter.this.display.getCode().setValue(returnDTO.getCode());
				EditReturnPresenter.this.display.getDescription().setValue(returnDTO.getDescription());
				EditReturnPresenter.this.display.setInstitution(returnDTO.getInstitution());
				EditReturnPresenter.this.display.setTemplate(returnDTO.getTemplate());
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving return");
			}
		});
		
	}
	
	private void bind() {
		
		this.display.getSaveButton().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				if(checkFields()){
					rpcService.isReturnCodeUnique(display.getCode().getValue(), returnDTO.getId(), new AsyncCallback<Boolean>() {
	
						public void onFailure(Throwable caught) {
							Window.alert("Error checking code uniqueness");
						}
	
						public void onSuccess(Boolean result) {
							if(result){
								doSave();
							} else {
								Window.alert("Return with this code is already registered");
							}
						}
					});
				}
			}
		});
		
		this.display.getCancelButton().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				eventBus.fireEvent(new EditReturnCancelledEvent());
			}
		});
		
	}

	private boolean checkFields() {
		String errorMessage = "";
		String codeField = display.getCode().getValue();
		String descField = display.getDescription().getValue();
		
		if(codeField != null && !codeField.matches("[A-Za-z0-9]+")){
			errorMessage += "Code contains invalid characters \n";
		}
		
		if(codeField == null || codeField.length() > 255 || codeField.length() == 0){
			errorMessage += "Code lenght must be between 1 and 255 \n";
		}
		
		if(descField != null && !descField.matches("[A-Za-z0-9 \\s]+")){
			errorMessage += "Description contains invalid characters \n";
		}
		
		if(descField == null || descField.length() > 255 || descField.length() == 0){
			errorMessage += "Description length must be between 1 and 255 \n";
		}
		
		if(!errorMessage.equals("")){
			Window.alert(errorMessage);
			return false;
		}
		
		return true;
	}
	
	private void doSave(){
		
		returnDTO.setCode(display.getCode().getValue());
		returnDTO.setDescription(display.getDescription().getValue());
		returnDTO.setInstitution(display.getInstitution());
		returnDTO.setTemplate(display.getTemplate());
		if(returnDTO.getId() > 0){
			rpcService.updateReturn(returnDTO, new AsyncCallback<ReturnDTO>() {
				
				public void onSuccess(ReturnDTO result) {
					eventBus.fireEvent(new ReturnUpdatedEvent(result));
				}
				
				public void onFailure(Throwable caught) {
					Window.alert("Error updating return");
				}
			});
		} else {
			rpcService.addReturn(returnDTO, new AsyncCallback<ReturnDTO>() {
				public void onSuccess(ReturnDTO result) {
					eventBus.fireEvent(new ReturnUpdatedEvent(result));
				}
				
				public void onFailure(Throwable caught) {
					Window.alert("Error adding return");
				}
			});
		}
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
