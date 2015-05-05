package com.vamekh.client;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.vamekh.shared.Schedule;

public class ScheduleListbox extends ListBox implements HasValue<Schedule>{

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Schedule> handler) {
		return null;
	}

	public Schedule getValue() {
		return Schedule.valueOf(getItemText(getSelectedIndex()));
	}

	public void setValue(Schedule value) {
		for(int i = 0; i < getItemCount(); i++){
			if(getItemText(i).equals(value.toString())){
				setSelectedIndex(i);
			}
		}
	}

	public void setValue(Schedule value, boolean fireEvents) {
		
	}

}
