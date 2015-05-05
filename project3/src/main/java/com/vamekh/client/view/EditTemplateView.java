package com.vamekh.client.view;

import java.util.Arrays;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.vamekh.client.presenter.EditTemplatePresenter;
import com.vamekh.shared.Schedule;

public class EditTemplateView extends Composite implements EditTemplatePresenter.Display{

	private FramedPanel panel;
	private VerticalLayoutContainer fieldContainer;
	private TextField code;
	private TextField description;
	private Schedule schedule;
	private ComboBox<Schedule> scheduleChooser;
	private TextButton saveButton;
	private TextButton cancelButton;
	private ButtonBar buttonPanel;
	
	public EditTemplateView(){
		
		panel = new FramedPanel();
		panel.setHeadingText("Template");
		panel.setWidth(400);
		panel.setLayoutData(new MarginData(20));
		
		initWidget(panel);
		
		initFields();
		
	}
	
	private void initFields() {
		
		fieldContainer = new VerticalLayoutContainer();
		buttonPanel = new ButtonBar();
		
		code = new TextField();
		code.addValidator(new RegExValidator("[A-Za-z0-9]+",
				"Code contains invalid characters"));
		code.addValidator(new MinLengthValidator(1));
		code.addValidator(new MaxLengthValidator(20));
		code.setAutoValidate(true);
		
		description = new TextField();
		description.addValidator(new RegExValidator("[A-Za-z0-9 \\s]+",
				"Description contains invalid characters"));
		description.addValidator(new MinLengthValidator(1));
		description.addValidator(new MaxLengthValidator(255));
		description.setAutoValidate(true);
		
		ListStore<Schedule> scheduleStore = new ListStore<Schedule>(new ModelKeyProvider<Schedule>() {

			public String getKey(Schedule item) {
				return "" + item.toString();
			}
			
		});
		
		scheduleStore.addAll(Arrays.asList(Schedule.values()));
		schedule = scheduleStore.get(0);
		scheduleChooser = new ComboBox<Schedule>(scheduleStore, new LabelProvider<Schedule>() {

			public String getLabel(Schedule item) {
				return item.toString();
			}
			
		});
		
		scheduleChooser.setEditable(false);
		scheduleChooser.setForceSelection(true);
		scheduleChooser.setTypeAhead(true);
		scheduleChooser.setTriggerAction(TriggerAction.ALL);
		scheduleChooser.setValue(schedule);
		scheduleChooser.addSelectionHandler(new SelectionHandler<Schedule>() {

			public void onSelection(SelectionEvent<Schedule> event) {
				schedule = event.getSelectedItem();
			}
		});
		
		saveButton = new TextButton("Save");
		cancelButton = new TextButton("Cancel");
		
		fieldContainer.add(new FieldLabel(code, "Code"));
		fieldContainer.add(new FieldLabel(description, "Description"));
		fieldContainer.add(scheduleChooser);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		fieldContainer.add(buttonPanel);
		panel.add(fieldContainer);
		
	}

	public HasSelectHandlers getSaveButton() {
		return saveButton;
	}

	public HasSelectHandlers getCancelButton() {
		return cancelButton;
	}

	public HasValue<String> getCode() {
		return code;
	}

	public HasValue<String> getDescription() {
		return description;
	}

	public Schedule getSchedule() {
		return schedule;
	}
	
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
		scheduleChooser.setValue(schedule);
	}
	
	public Widget asWidget() {
		return this;
	}

}
