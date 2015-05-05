package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.vamekh.shared.InstitutionDTO;

public class InstitutionUpdatedEvent extends GwtEvent<InstitutionUpdatedEventHandler>{
	
	public static Type<InstitutionUpdatedEventHandler> TYPE = new Type<InstitutionUpdatedEventHandler>();
	private InstitutionDTO updatedInstitution;
	
	public InstitutionUpdatedEvent(InstitutionDTO updatedInstitution){
		this.updatedInstitution = updatedInstitution;
	}
	
	public InstitutionDTO getUpdatedInstitution(){ return updatedInstitution; }
	
	@Override
	public Type<InstitutionUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(InstitutionUpdatedEventHandler handler) {
		handler.onInstitutionUpdated(this);
	}
	

}
