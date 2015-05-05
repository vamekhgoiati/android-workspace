package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditReturnEvent extends GwtEvent<EditReturnEventHandler>{
	
	public static Type<EditReturnEventHandler> TYPE = new Type<EditReturnEventHandler>();
	private int id;
	
	public EditReturnEvent(int id){
		this.id = id;
	}
	
	public int getId(){ return id; }
	
	@Override
	public Type<EditReturnEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditReturnEventHandler handler) {
		handler.onEditReturn(this);
	};
	
	
	
}
