package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditInstitutionCancelledEvent extends GwtEvent<EditInstitutionCancelledEventHandler>{

	public static Type<EditInstitutionCancelledEventHandler> TYPE = new Type<EditInstitutionCancelledEventHandler>();
	
	@Override
	public Type<EditInstitutionCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditInstitutionCancelledEventHandler handler) {
		handler.onEditInstitutionCancelled(this);
	}
	

}
