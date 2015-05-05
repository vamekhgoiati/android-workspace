package com.vamekh.client.presenter;

import javax.persistence.TemporalType;

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
import com.vamekh.client.TemplateServiceAsync;
import com.vamekh.client.event.EditTemplateCancelledEvent;
import com.vamekh.client.event.TemplateUpdatedEvent;
import com.vamekh.shared.Schedule;
import com.vamekh.shared.TemplateDTO;

public class EditTemplatePresenter implements Presenter {
	public interface Display {
		HasSelectHandlers getSaveButton();

		HasSelectHandlers getCancelButton();

		HasValue<String> getCode();

		HasValue<String> getDescription();

		Schedule getSchedule();
		
		void setSchedule(Schedule schedule);

		Widget asWidget();
	}

	private TemplateDTO templateDTO;
	private final TemplateServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	
	public EditTemplatePresenter(TemplateServiceAsync rpcService, HandlerManager eventBus, Display display){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.templateDTO = new TemplateDTO();
		bind();
	}
	
	public EditTemplatePresenter(TemplateServiceAsync rpcService, HandlerManager eventBus, Display display, int id){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		bind();
		
		rpcService.getTemplate(id, new AsyncCallback<TemplateDTO>() {

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving template");
			}

			public void onSuccess(TemplateDTO result) {
				templateDTO = result;
				EditTemplatePresenter.this.display.getCode().setValue(templateDTO.getCode());
				EditTemplatePresenter.this.display.getDescription().setValue(templateDTO.getDescription());
				EditTemplatePresenter.this.display.setSchedule(templateDTO.getSchedule());
			}
			
		});
	}
	
	private void bind() {
		this.display.getSaveButton().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				if(checkFields()){
					rpcService.codeIsUnique(display.getCode().getValue(), templateDTO.getId(), new AsyncCallback<Boolean>() {
	
						public void onFailure(Throwable caught) {
							Window.alert("Error checking code uniqueness");
						}
	
						public void onSuccess(Boolean result) {
							if(result){
								doSave();
							} else {
								Window.alert("Template with this code is already registered");
							}
						}
					});
				}
			}
		});
		
		this.display.getCancelButton().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				eventBus.fireEvent(new EditTemplateCancelledEvent());
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

	private void doSave() {
		
		templateDTO.setCode(display.getCode().getValue());
		templateDTO.setDescription(display.getDescription().getValue());
		templateDTO.setSchedule(display.getSchedule());
		if(templateDTO.getId() > 0){
			rpcService.updateTemplate(templateDTO, new AsyncCallback<TemplateDTO>() {
				
				public void onSuccess(TemplateDTO result) {
					eventBus.fireEvent(new TemplateUpdatedEvent(result));
				}
				
				public void onFailure(Throwable caught) {
					Window.alert("Error updating template");
				}
			});
		} else {
			rpcService.addTemplate(templateDTO, new AsyncCallback<TemplateDTO>() {
				public void onSuccess(TemplateDTO result) {
					eventBus.fireEvent(new TemplateUpdatedEvent(result));
				}
				
				public void onFailure(Throwable caught) {
					Window.alert("Error adding template");
				}
			});
		}
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
