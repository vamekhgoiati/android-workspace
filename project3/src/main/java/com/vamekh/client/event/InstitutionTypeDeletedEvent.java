package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class InstitutionTypeDeletedEvent extends GwtEvent<InstitutionTypeDeletedEventHandler>{

	public static Type<InstitutionTypeDeletedEventHandler> TYPE = new Type<InstitutionTypeDeletedEventHandler>();
	
	@Override
	public Type<InstitutionTypeDeletedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(InstitutionTypeDeletedEventHandler handler) {
		handler.onInstitutionTypeDeleted(this);
	}

}
