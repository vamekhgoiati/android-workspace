package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddReturnEvent extends GwtEvent<AddReturnEventHandler>{
	
	public static Type<AddReturnEventHandler> TYPE = new Type<AddReturnEventHandler>();

	@Override
	public Type<AddReturnEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddReturnEventHandler handler) {
		handler.onAddReturn(this);
	}
	
	

}
