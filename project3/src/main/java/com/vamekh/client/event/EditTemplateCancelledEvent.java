package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditTemplateCancelledEvent extends GwtEvent<EditTemplateCancelledEventHandler>{
	public static Type<EditTemplateCancelledEventHandler> TYPE = new Type<EditTemplateCancelledEventHandler>();

	@Override
	public Type<EditTemplateCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditTemplateCancelledEventHandler handler) {
		handler.onEditTemplateCancelled(this);
	}
	
	

}
