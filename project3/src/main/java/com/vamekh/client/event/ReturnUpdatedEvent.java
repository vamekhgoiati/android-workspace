package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.vamekh.shared.ReturnDTO;

public class ReturnUpdatedEvent extends GwtEvent<ReturnUpdatedEventHandler>{
	
	public static Type<ReturnUpdatedEventHandler> TYPE = new Type<ReturnUpdatedEventHandler>();
	private ReturnDTO updatedReturn;

	public ReturnUpdatedEvent(ReturnDTO updatedReturn){
		this.updatedReturn = updatedReturn;
	}
	
	public ReturnDTO getUpdatedReturn(){ return updatedReturn; }

	@Override
	public Type<ReturnUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReturnUpdatedEventHandler handler) {
		handler.onReturnUpdated(this);
	}
	
	
	
}
