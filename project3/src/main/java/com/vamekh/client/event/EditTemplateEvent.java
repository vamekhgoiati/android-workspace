package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;


public class EditTemplateEvent extends GwtEvent<EditTemplateEventHandler>{
	
	public static Type<EditTemplateEventHandler> TYPE = new Type<EditTemplateEventHandler>();
	private final int id;
	
	public EditTemplateEvent(int id){
		this.id = id;
	}
	
	public int getId(){ return id; }
	
	@Override
	public Type<EditTemplateEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(EditTemplateEventHandler handler) {
		handler.onEditTemplate(this);
	}

}
