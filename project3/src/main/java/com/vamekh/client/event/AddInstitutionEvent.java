package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddInstitutionEvent extends GwtEvent<AddInstitutionEventHandler>{
	
	public static Type<AddInstitutionEventHandler> TYPE = new Type<AddInstitutionEventHandler>();
	
	@Override
	public Type<AddInstitutionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddInstitutionEventHandler handler) {
		handler.onAddInstitution(this);
	}
	

}
