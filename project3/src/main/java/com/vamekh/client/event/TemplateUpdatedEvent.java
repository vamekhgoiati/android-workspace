package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.vamekh.shared.TemplateDTO;

public class TemplateUpdatedEvent extends GwtEvent<TemplateUpdatedEventHandler>{
	public static Type<TemplateUpdatedEventHandler> TYPE = new Type<TemplateUpdatedEventHandler>();
	private TemplateDTO updatedTemplate;
	
	public TemplateUpdatedEvent(TemplateDTO updatedTemplate){
		this.updatedTemplate = updatedTemplate;
	}
	
	public TemplateDTO getUpdatedTemplate(){ return updatedTemplate; }
	
	@Override
	public Type<TemplateUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}
	@Override
	protected void dispatch(TemplateUpdatedEventHandler handler) {
		handler.onTemplateUpdated(this);
	}
	
	
}
