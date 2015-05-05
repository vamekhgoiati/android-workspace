package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class InstitutionDeletedEvent extends GwtEvent<InstitutionDeletedEventHandler>{

	public static Type<InstitutionDeletedEventHandler> TYPE = new Type<InstitutionDeletedEventHandler>();
	
	@Override
	public Type<InstitutionDeletedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(InstitutionDeletedEventHandler handler) {
		handler.onInstitutionDeleted(this);
	}

}
