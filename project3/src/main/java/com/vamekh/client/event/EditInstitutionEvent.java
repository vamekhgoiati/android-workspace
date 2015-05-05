package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditInstitutionEvent extends GwtEvent<EditInstitutionEventHandler>{

	public static Type<EditInstitutionEventHandler> TYPE = new Type<EditInstitutionEventHandler>();
	private final int id;
	
	public EditInstitutionEvent(int id){
		this.id = id;
	}
	
	public int getId(){ return id; }
	
	@Override
	public Type<EditInstitutionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditInstitutionEventHandler handler) {
		handler.onEditInstitution(this);
	}

}
