package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditReturnCancelledEvent extends GwtEvent<EditReturnCancelledEventHandler>{
	
	public static Type<EditReturnCancelledEventHandler> TYPE = new Type<EditReturnCancelledEventHandler>();

	@Override
	public Type<EditReturnCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditReturnCancelledEventHandler handler) {
		handler.onEditReturnCancelled(this);
	}
	
	

}
