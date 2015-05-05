package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class TemplateDeletedEvent extends GwtEvent<TemplateDeletedEventHandler>{
	
	public static Type<TemplateDeletedEventHandler> TYPE = new Type<TemplateDeletedEventHandler>();
	
	@Override
	public Type<TemplateDeletedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	  protected void dispatch(TemplateDeletedEventHandler handler) {
	    handler.onTemplateDeleted(this);
	  }
	
	

}
