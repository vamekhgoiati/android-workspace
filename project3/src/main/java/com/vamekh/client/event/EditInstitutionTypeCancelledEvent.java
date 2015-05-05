package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditInstitutionTypeCancelledEvent extends GwtEvent<EditInstitutionTypeCancelledEventHandler>{

	public static Type<EditInstitutionTypeCancelledEventHandler> TYPE = new Type<EditInstitutionTypeCancelledEventHandler>();
	
	@Override
	public Type<EditInstitutionTypeCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditInstitutionTypeCancelledEventHandler handler) {
		handler.onEditInstitutionTypeCancelled(this);
	}
	

}
