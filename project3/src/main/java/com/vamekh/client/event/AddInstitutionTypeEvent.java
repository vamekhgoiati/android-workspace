package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddInstitutionTypeEvent extends GwtEvent<AddInstitutionTypeEventHandler>{

	public static Type<AddInstitutionTypeEventHandler> TYPE = new Type<AddInstitutionTypeEventHandler>();
	
	@Override
	public Type<AddInstitutionTypeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddInstitutionTypeEventHandler handler) {
		handler.onAddInstitutionType(this);
	}

}
