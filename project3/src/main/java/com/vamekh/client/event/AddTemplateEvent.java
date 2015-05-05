package com.vamekh.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class AddTemplateEvent extends GwtEvent<AddTemplateEventHandler> {
	public static Type<AddTemplateEventHandler> TYPE = new Type<AddTemplateEventHandler>();

	@Override
	public Type<AddTemplateEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddTemplateEventHandler handler) {
		handler.onAddTemplate(this);
	}
}
