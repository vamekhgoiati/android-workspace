package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.vamekh.shared.InstitutionTypeDTO;

public class InstitutionTypeUpdatedEvent extends GwtEvent<InstitutionTypeUpdatedEventHandler>{

	public static Type<InstitutionTypeUpdatedEventHandler> TYPE = new Type<InstitutionTypeUpdatedEventHandler>();
	private InstitutionTypeDTO updatedType;
	
	public InstitutionTypeUpdatedEvent(InstitutionTypeDTO updatedType){
		this.updatedType = updatedType;
	}
	
	public InstitutionTypeDTO getUpdatedType(){ return updatedType; }
	
	@Override
	public Type<InstitutionTypeUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(InstitutionTypeUpdatedEventHandler handler) {
		handler.onInstitutionTypeUpdated(this);
	}

	
}
