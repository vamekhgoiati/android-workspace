package com.vamekh.client.view;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.eclipse.jdt.internal.compiler.flow.LabelFlowContext;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.vamekh.client.presenter.EditInstitutionTypePresenter;

public class EditInstitutionTypeView extends Composite implements EditInstitutionTypePresenter.Display{
	
	private TextField code;
	private TextField name;
	private TextButton saveButton;
	private TextButton cancelButton;
	private FramedPanel panel;
	
	public EditInstitutionTypeView(){
		
		panel = new FramedPanel();
		panel.setHeadingText("Institution Type");
		panel.setWidth(400);
		panel.setLayoutData(new MarginData(20));
		
		VerticalLayoutContainer fieldContainer = new VerticalLayoutContainer();

		initWidget(panel);
		
		ButtonBar buttonPanel = new ButtonBar();
		code = new TextField();
		code.addValidator(new RegExValidator("[A-Za-z0-9]+", "Code contains invalid characters"));
		code.addValidator(new MaxLengthValidator(20));
		code.addValidator(new MinLengthValidator(1));
		code.setAutoValidate(true);
		fieldContainer.add(new FieldLabel(code, "Code"));
		
		name = new TextField();
		name.addValidator(new RegExValidator("[A-Za-z0-9 \\s]+", "Name contains invalid characters"));
		name.addValidator(new MaxLengthValidator(255));
		name.addValidator(new MinLengthValidator(1));
		name.setAutoValidate(true);
		fieldContainer.add(new FieldLabel(name, "Name"));
		
		saveButton = new TextButton("Save");
		cancelButton = new TextButton("Cancel");
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

	public HasValue<String> getName() {
		return name;
	}
	
	public Widget asWidget(){
		return this;
	}

	
	
}
