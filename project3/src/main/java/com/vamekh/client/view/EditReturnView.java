package com.vamekh.client.view;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
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
import com.vamekh.client.InstitutionProperties;
import com.vamekh.client.InstitutionService;
import com.vamekh.client.InstitutionServiceAsync;
import com.vamekh.client.TemplateProperties;
import com.vamekh.client.TemplateService;
import com.vamekh.client.TemplateServiceAsync;
import com.vamekh.client.presenter.EditReturnPresenter;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionTypeDTO;
import com.vamekh.shared.TemplateDTO;

public class EditReturnView extends Composite implements EditReturnPresenter.Display {

	private FramedPanel panel;
	private VerticalLayoutContainer fieldContainer;
	private TextField code;
	private TextField description;
	private TextButton saveButton;
	private TextButton cancelButton;
	private InstitutionDTO institution;
	private TemplateDTO template;
	private ComboBox<InstitutionDTO> institutionChooser;
	private ComboBox<TemplateDTO> templateChooser;
	private ButtonBar buttonPanel;
	private InstitutionProperties instProps;
	private TemplateProperties templateProps;
	
	public EditReturnView(){
		
		panel = new FramedPanel();
		panel.setHeadingText("Return");
		panel.setWidth(400);
		panel.setLayoutData(new MarginData(20));

		initWidget(panel);
		
		instProps = GWT.create(InstitutionProperties.class);
		templateProps = GWT.create(TemplateProperties.class);
		
		initFields();

		
	}
	
	private void initFields() {
		
		InstitutionServiceAsync instRpcService = GWT.create(InstitutionService.class);
		TemplateServiceAsync templRpcService = GWT.create(TemplateService.class);
		
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
		
		final ListStore<InstitutionDTO> instStore = new ListStore<InstitutionDTO>(
				new ModelKeyProvider<InstitutionDTO>() {

					public String getKey(InstitutionDTO item) {
						return "" + item.getId();
					}

				});

		instRpcService
				.getInstitutions(new AsyncCallback<ArrayList<InstitutionDTO>>() {

					public void onSuccess(ArrayList<InstitutionDTO> result) {
						instStore.addAll(result);
						institution = instStore.get(0);
						institutionChooser.setValue(institution);
					}

					public void onFailure(Throwable caught) {
						Window.alert("Error loading institutions");
					}
				});

		institutionChooser = new ComboBox<InstitutionDTO>(instStore,
				instProps.codeLabel());
		institutionChooser.setEditable(false);
		institutionChooser.setForceSelection(true);
		institutionChooser.setTypeAhead(true);
		institutionChooser.setTriggerAction(TriggerAction.ALL);

		institutionChooser.addSelectionHandler(new SelectionHandler<InstitutionDTO>() {

			public void onSelection(SelectionEvent<InstitutionDTO> event) {
				institution = event.getSelectedItem();
			}
		});
		
		
		final ListStore<TemplateDTO> templateStore = new ListStore<TemplateDTO>(
				new ModelKeyProvider<TemplateDTO>() {

					public String getKey(TemplateDTO item) {
						return "" + item.getId();
					}

				});

		templRpcService
				.getTemplates(new AsyncCallback<ArrayList<TemplateDTO>>() {

					public void onSuccess(ArrayList<TemplateDTO> result) {
						templateStore.addAll(result);
						template = templateStore.get(0);
						templateChooser.setValue(template);
					}

					public void onFailure(Throwable caught) {
						Window.alert("Error loading templates");
					}
				});

		templateChooser = new ComboBox<TemplateDTO>(templateStore,
				templateProps.codeLabel());
		templateChooser.setEditable(false);
		templateChooser.setForceSelection(true);
		templateChooser.setTypeAhead(true);
		templateChooser.setTriggerAction(TriggerAction.ALL);

		templateChooser.addSelectionHandler(new SelectionHandler<TemplateDTO>() {

			public void onSelection(SelectionEvent<TemplateDTO> event) {
				template = event.getSelectedItem();
			}
		});
		
		saveButton = new TextButton("Save");
		cancelButton = new TextButton("Cancel");
		
		fieldContainer.add(new FieldLabel(code, "Code"));
		fieldContainer.add(new FieldLabel(description, "Description"));
		fieldContainer.add(new FieldLabel(institutionChooser, "Institution"));
		fieldContainer.add(new FieldLabel(templateChooser, "Template"));
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

	public InstitutionDTO getInstitution() {
		return institution;
	}

	public void setInstitution(InstitutionDTO inst) {
		this.institution = inst;
		institutionChooser.setValue(institution);
	}

	public TemplateDTO getTemplate() {
		return template;
	}

	public void setTemplate(TemplateDTO template) {
		this.template = template;
		templateChooser.setValue(template);
	}
	
	public Widget asWidget() {
		return this;
	}

}
