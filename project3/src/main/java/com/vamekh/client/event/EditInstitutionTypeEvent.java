package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditInstitutionTypeEvent extends GwtEvent<EditInstitutionTypeEventHandler>{

	public static Type<EditInstitutionTypeEventHandler> TYPE = new Type<EditInstitutionTypeEventHandler>();
	private final int id;
	
	public EditInstitutionTypeEvent(int id){
		this.id = id;
	}
	
	public int getId(){ return id; }
	
	@Override
	public Type<EditInstitutionTypeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditInstitutionTypeEventHandler handler) {
		handler.onEditInstitutionType(this);
	}

}
