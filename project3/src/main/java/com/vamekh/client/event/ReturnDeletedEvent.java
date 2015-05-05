package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ReturnDeletedEvent extends GwtEvent<ReturnDeletedEventHandler>{
	
	public static Type<ReturnDeletedEventHandler> TYPE = new Type<ReturnDeletedEventHandler>();

	@Override
	public Type<ReturnDeletedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReturnDeletedEventHandler handler) {
		handler.onReturnDeleted(this);
	}
	
	

}
