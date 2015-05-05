package com.vamekh.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.RefreshEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.HasRowClickHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionTypeDTO;
import com.vamekh.shared.ReturnDTO;
import com.vamekh.shared.Schedule;
import com.vamekh.shared.TemplateDTO;
import com.vamekh.client.InstitutionProperties;
import com.vamekh.client.InstitutionService;
import com.vamekh.client.InstitutionServiceAsync;
import com.vamekh.client.InstitutionTypeService;
import com.vamekh.client.InstitutionTypeServiceAsync;
import com.vamekh.client.ReturnProperties;
import com.vamekh.client.ReturnService;
import com.vamekh.client.ReturnServiceAsync;
import com.vamekh.client.TemplateProperties;
import com.vamekh.client.TemplateService;
import com.vamekh.client.TemplateServiceAsync;
import com.vamekh.client.TypeProperties;
import com.vamekh.client.presenter.MainPresenter;

@UiTemplate("MainView.ui.xml")
public class MainView extends Composite implements MainPresenter.Display {

	private String currentList;

	@UiField
	VBoxLayoutContainer buttonBox;

	@UiField
	CardLayoutContainer layout;

	@UiField
	ToggleButton fiTypes, fi, templates, returns;

	@UiField(provided = true)
	ColumnModel<InstitutionTypeDTO> cmType;

	@UiField(provided = true)
	ColumnModel<InstitutionDTO> cmInst;

	@UiField(provided = true)
	ColumnModel<TemplateDTO> cmTemplate;

	@UiField(provided = true)
	ColumnModel<ReturnDTO> cmReturn;

	@UiField(provided = true)
	CheckBoxSelectionModel<InstitutionTypeDTO> smType;

	@UiField(provided = true)
	CheckBoxSelectionModel<InstitutionDTO> smInst;

	@UiField(provided = true)
	CheckBoxSelectionModel<TemplateDTO> smTemplate;

	@UiField(provided = true)
	CheckBoxSelectionModel<ReturnDTO> smReturn;

	@UiField(provided = true)
	ListStore<InstitutionTypeDTO> storeType;

	@UiField(provided = true)
	ListStore<InstitutionDTO> storeInst;

	@UiField(provided = true)
	ListStore<TemplateDTO> storeTemplate;

	@UiField(provided = true)
	ListStore<ReturnDTO> storeReturn;

	@UiField(provided = true)
	PagingLoader<PagingLoadConfig, PagingLoadResult<InstitutionTypeDTO>> loaderType;

	@UiField(provided = true)
	PagingLoader<PagingLoadConfig, PagingLoadResult<InstitutionDTO>> loaderInst;

	@UiField(provided = true)
	PagingLoader<PagingLoadConfig, PagingLoadResult<TemplateDTO>> loaderTemplate;

	@UiField(provided = true)
	PagingLoader<PagingLoadConfig, PagingLoadResult<ReturnDTO>> loaderReturn;

	@UiField
	Grid<InstitutionTypeDTO> gridInstType;

	@UiField
	Grid<InstitutionDTO> gridInst;

	@UiField
	Grid<TemplateDTO> gridTemplate;

	@UiField
	Grid<ReturnDTO> gridReturn;

	@UiField
	GridView<InstitutionTypeDTO> viewType;

	@UiField
	GridView<InstitutionDTO> viewInst;

	@UiField
	GridView<TemplateDTO> viewTemplate;

	@UiField
	GridView<ReturnDTO> viewReturn;

	@UiField
	PagingToolBar typeToolBar;

	@UiField
	PagingToolBar instToolBar;

	@UiField
	PagingToolBar templateToolBar;

	@UiField
	PagingToolBar returnToolBar;

	// @UiField
	// CheckBox warnLoad;

	@UiField
	TextButton addTypeButton;

	@UiField
	TextButton editTypeButton;

	@UiField
	TextButton deleteTypeButton;

	@UiField
	TextButton addInstButton;

	@UiField
	TextButton editInstButton;

	@UiField
	TextButton deleteInstButton;

	@UiField
	TextButton addTemplateButton;

	@UiField
	TextButton editTemplateButton;

	@UiField
	TextButton deleteTemplateButton;

	@UiField
	TextButton addReturnButton;

	@UiField
	TextButton editReturnButton;

	@UiField
	TextButton deleteReturnButton;

	private TypeProperties typeProps;

	private InstitutionProperties instProps;

	private TemplateProperties templateProps;

	private ReturnProperties returnProps;

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}

	private static MainViewUiBinder uiBinder = GWT
			.create(MainViewUiBinder.class);

	public MainView() {

		this(0);

	}

	public MainView(int currList) {

		initTypeGrid();
		initInstGrid();
		initTemplateGrid();
		initReturnGrid();

		initWidget(uiBinder.createAndBindUi(this));

		typeToolBar.getElement().getStyle().setProperty("borderBottom", "none");
		typeToolBar.bind(loaderType);

		instToolBar.getElement().getStyle().setProperty("borderBottom", "none");
		instToolBar.bind(loaderInst);

		templateToolBar.getElement().getStyle()
				.setProperty("borderBottom", "none");
		templateToolBar.bind(loaderTemplate);

		returnToolBar.getElement().getStyle()
				.setProperty("borderBottom", "none");
		returnToolBar.bind(loaderReturn);
		
		layout.setActiveWidget(layout.getWidget(currList));

	}

	private void initReturnGrid() {

		returnProps = GWT.create(ReturnProperties.class);
		final ReturnServiceAsync returnRpcService = GWT
				.create(ReturnService.class);

		storeReturn = new ListStore<ReturnDTO>(
				new ModelKeyProvider<ReturnDTO>() {

					public String getKey(ReturnDTO item) {
						return "" + item.getId();
					}

				});

		RpcProxy<PagingLoadConfig, PagingLoadResult<ReturnDTO>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<ReturnDTO>>() {

			@Override
			public void load(PagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<ReturnDTO>> callback) {
				returnRpcService.getReturns(loadConfig, callback);

			}
		};

		PagingLoader<PagingLoadConfig, PagingLoadResult<ReturnDTO>> pageLoader = new PagingLoader<PagingLoadConfig, PagingLoadResult<ReturnDTO>>(
				proxy);

		loaderReturn = pageLoader;
		loaderReturn.setRemoteSort(true);
		loaderReturn
				.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, ReturnDTO, PagingLoadResult<ReturnDTO>>(
						storeReturn));

		IdentityValueProvider<ReturnDTO> identity = new IdentityValueProvider<ReturnDTO>();
		smReturn = new CheckBoxSelectionModel<ReturnDTO>(identity) {
			@Override
			protected void onRefresh(RefreshEvent event) {
				// this code selects all rows when paging if the header checkbox
				// is selected
				if (isSelectAllChecked()) {
					selectAll();
				}
				super.onRefresh(event);
			}
		};

		ColumnConfig<ReturnDTO, String> codeColumn = new ColumnConfig<ReturnDTO, String>(
				returnProps.code(), 150, "Code");
		ColumnConfig<ReturnDTO, String> descColumn = new ColumnConfig<ReturnDTO, String>(
				returnProps.description(), 150, "Description");
		ColumnConfig<ReturnDTO, String> instColumn = new ColumnConfig<ReturnDTO, String>(
				returnProps.institutionCode(), 150, "FI Code");
		ColumnConfig<ReturnDTO, String> tmplColumn = new ColumnConfig<ReturnDTO, String>(
				returnProps.templateCode(), 150, "Template Code");

		List<ColumnConfig<ReturnDTO, ?>> l = new ArrayList<ColumnConfig<ReturnDTO, ?>>();
		l.add(smReturn.getColumn());
		l.add(codeColumn);
		l.add(descColumn);
		l.add(instColumn);
		l.add(tmplColumn);

		cmReturn = new ColumnModel<ReturnDTO>(l);

		Timer t = new Timer() {

			@Override
			public void run() {
				loaderReturn.load();
			}
		};
		t.schedule(100);

	}

	private void initTemplateGrid() {

		templateProps = GWT.create(TemplateProperties.class);
		final TemplateServiceAsync templateRpcService = GWT
				.create(TemplateService.class);

		storeTemplate = new ListStore<TemplateDTO>(
				new ModelKeyProvider<TemplateDTO>() {

					public String getKey(TemplateDTO item) {
						return "" + item.getId();
					}

				});

		RpcProxy<PagingLoadConfig, PagingLoadResult<TemplateDTO>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<TemplateDTO>>() {

			@Override
			public void load(PagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<TemplateDTO>> callback) {
				templateRpcService.getTemplates(loadConfig, callback);

			}
		};

		PagingLoader<PagingLoadConfig, PagingLoadResult<TemplateDTO>> pageLoader = new PagingLoader<PagingLoadConfig, PagingLoadResult<TemplateDTO>>(
				proxy);

		loaderTemplate = pageLoader;
		loaderTemplate.setRemoteSort(true);
		loaderTemplate
				.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, TemplateDTO, PagingLoadResult<TemplateDTO>>(
						storeTemplate));

		IdentityValueProvider<TemplateDTO> identity = new IdentityValueProvider<TemplateDTO>();
		smTemplate = new CheckBoxSelectionModel<TemplateDTO>(identity) {
			@Override
			protected void onRefresh(RefreshEvent event) {
				// this code selects all rows when paging if the header checkbox
				// is selected
				if (isSelectAllChecked()) {
					selectAll();
				}
				super.onRefresh(event);
			}
		};

		ColumnConfig<TemplateDTO, String> codeColumn = new ColumnConfig<TemplateDTO, String>(
				templateProps.code(), 150, "Code");
		ColumnConfig<TemplateDTO, String> descColumn = new ColumnConfig<TemplateDTO, String>(
				templateProps.description(), 150, "Description");
		ColumnConfig<TemplateDTO, String> scheduleColumn = new ColumnConfig<TemplateDTO, String>(
				templateProps.scheduleString(), 150, "Schedule");
		
		List<ColumnConfig<TemplateDTO, ?>> l = new ArrayList<ColumnConfig<TemplateDTO, ?>>();
		l.add(smTemplate.getColumn());
		l.add(codeColumn);
		l.add(descColumn);
		l.add(scheduleColumn);

		cmTemplate = new ColumnModel<TemplateDTO>(l);

		Timer t = new Timer() {

			@Override
			public void run() {
				loaderTemplate.load();
			}
		};
		t.schedule(100);

	}

	private void initInstGrid() {

		instProps = GWT.create(InstitutionProperties.class);
		final InstitutionServiceAsync instRpcService = GWT
				.create(InstitutionService.class);

		storeInst = new ListStore<InstitutionDTO>(
				new ModelKeyProvider<InstitutionDTO>() {

					public String getKey(InstitutionDTO item) {
						return "" + item.getId();
					}

				});

		RpcProxy<PagingLoadConfig, PagingLoadResult<InstitutionDTO>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<InstitutionDTO>>() {

			@Override
			public void load(PagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<InstitutionDTO>> callback) {
				instRpcService.getInstitutions(loadConfig, callback);

			}
		};

		PagingLoader<PagingLoadConfig, PagingLoadResult<InstitutionDTO>> pageLoader = new PagingLoader<PagingLoadConfig, PagingLoadResult<InstitutionDTO>>(
				proxy);

		loaderInst = pageLoader;
		loaderInst.setRemoteSort(true);
		loaderInst
				.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, InstitutionDTO, PagingLoadResult<InstitutionDTO>>(
						storeInst));

		IdentityValueProvider<InstitutionDTO> identity = new IdentityValueProvider<InstitutionDTO>();
		smInst = new CheckBoxSelectionModel<InstitutionDTO>(identity) {
			@Override
			protected void onRefresh(RefreshEvent event) {
				// this code selects all rows when paging if the header checkbox
				// is selected
				if (isSelectAllChecked()) {
					selectAll();
				}
				super.onRefresh(event);
			}
		};

		ColumnConfig<InstitutionDTO, String> codeColumn = new ColumnConfig<InstitutionDTO, String>(
				instProps.code(), 150, "Code");
		ColumnConfig<InstitutionDTO, String> nameColumn = new ColumnConfig<InstitutionDTO, String>(
				instProps.name(), 150, "Name");
		ColumnConfig<InstitutionDTO, String> addressColumn = new ColumnConfig<InstitutionDTO, String>(
				instProps.address(), 150, "Address");
		ColumnConfig<InstitutionDTO, String> phoneColumn = new ColumnConfig<InstitutionDTO, String>(
				instProps.phone(), 150, "Phone");
		ColumnConfig<InstitutionDTO, String> emailColumn = new ColumnConfig<InstitutionDTO, String>(
				instProps.email(), 150, "Email");
		ColumnConfig<InstitutionDTO, String> faxColumn = new ColumnConfig<InstitutionDTO, String>(
				instProps.fax(), 150, "Fax");
		ColumnConfig<InstitutionDTO, String> typeColumn = new ColumnConfig<InstitutionDTO, String>(
				instProps.typeCode(), 150, "Type Code");
		ColumnConfig<InstitutionDTO, Date> regColumn = new ColumnConfig<InstitutionDTO, Date>(
				instProps.regDate(), 150, "Date");
		regColumn.setCell(new DateCell(DateTimeFormat
				.getFormat(PredefinedFormat.DATE_SHORT)));

		List<ColumnConfig<InstitutionDTO, ?>> l = new ArrayList<ColumnConfig<InstitutionDTO, ?>>();
		l.add(smInst.getColumn());
		l.add(codeColumn);
		l.add(nameColumn);
		l.add(addressColumn);
		l.add(phoneColumn);
		l.add(emailColumn);
		l.add(faxColumn);
		l.add(typeColumn);
		l.add(regColumn);

		cmInst = new ColumnModel<InstitutionDTO>(l);

		Timer t = new Timer() {

			@Override
			public void run() {
				loaderInst.load();
			}
		};
		t.schedule(100);

	}

	private void initTypeGrid() {

		typeProps = GWT.create(TypeProperties.class);
		final InstitutionTypeServiceAsync typeRpcService = GWT
				.create(InstitutionTypeService.class);

		storeType = new ListStore<InstitutionTypeDTO>(
				new ModelKeyProvider<InstitutionTypeDTO>() {

					public String getKey(InstitutionTypeDTO item) {
						return "" + item.getId();
					}

				});

		RpcProxy<PagingLoadConfig, PagingLoadResult<InstitutionTypeDTO>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<InstitutionTypeDTO>>() {

			@Override
			public void load(PagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<InstitutionTypeDTO>> callback) {
				typeRpcService.getInstitutionTypes(loadConfig, callback);

			}
		};

		PagingLoader<PagingLoadConfig, PagingLoadResult<InstitutionTypeDTO>> pageLoader = new PagingLoader<PagingLoadConfig, PagingLoadResult<InstitutionTypeDTO>>(
				proxy);

		loaderType = pageLoader;
		loaderType.setRemoteSort(true);
		loaderType
				.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, InstitutionTypeDTO, PagingLoadResult<InstitutionTypeDTO>>(
						storeType));

		IdentityValueProvider<InstitutionTypeDTO> identity = new IdentityValueProvider<InstitutionTypeDTO>();
		smType = new CheckBoxSelectionModel<InstitutionTypeDTO>(identity) {
			@Override
			protected void onRefresh(RefreshEvent event) {
				// this code selects all rows when paging if the header checkbox
				// is selected
				if (isSelectAllChecked()) {
					selectAll();
				}
				super.onRefresh(event);
			}
		};

		ColumnConfig<InstitutionTypeDTO, String> codeColumn = new ColumnConfig<InstitutionTypeDTO, String>(
				typeProps.code(), 50, "Code");
		ColumnConfig<InstitutionTypeDTO, String> nameColumn = new ColumnConfig<InstitutionTypeDTO, String>(
				typeProps.name(), 150, "Name");

		List<ColumnConfig<InstitutionTypeDTO, ?>> l = new ArrayList<ColumnConfig<InstitutionTypeDTO, ?>>();
		l.add(smType.getColumn());
		l.add(codeColumn);
		l.add(nameColumn);

		cmType = new ColumnModel<InstitutionTypeDTO>(l);

		Timer t = new Timer() {

			@Override
			public void run() {
				loaderType.load();
			}
		};
		t.schedule(100);

	}

	@UiHandler({ "fi", "fiTypes", "templates", "returns" })
	public void buttonClicked(SelectEvent event) {
		ToggleButton button = (ToggleButton) event.getSource();

		int index = buttonBox.getWidgetIndex(button);
		layout.setActiveWidget(layout.getWidget(index));
	}

	public HasSelectHandlers getAddTypeButton() {
		return addTypeButton;
	}

	public HasSelectHandlers getEditTypeButton() {
		return editTypeButton;
	}

	public HasSelectHandlers getDeleteTypeButton() {
		return deleteTypeButton;
	}

	public HasSelectHandlers getAddInstButton() {
		return addInstButton;
	}

	public HasSelectHandlers getEditInstButton() {
		return editInstButton;
	}

	public HasSelectHandlers getDeleteInstButton() {
		return deleteInstButton;
	}

	public HasSelectHandlers getAddTemplateButton() {
		return addTemplateButton;
	}

	public HasSelectHandlers getEditTemplateButton() {
		return editTemplateButton;
	}

	public HasSelectHandlers getDeleteTemplateButton() {
		return deleteTemplateButton;
	}

	public HasSelectHandlers getAddReturnButton() {
		return addReturnButton;
	}

	public HasSelectHandlers getEditReturnButton() {
		return editReturnButton;
	}

	public HasSelectHandlers getDeleteReturnButton() {
		return deleteReturnButton;
	}

	public void refreshTypeData() {
		typeToolBar.refresh();
	}

	public void refreshInstitutionData() {
		instToolBar.refresh();
	}

	public void refreshTemplateData() {
		templateToolBar.refresh();
	}

	public void refreshReturnData() {
		returnToolBar.refresh();
	}

	public List<InstitutionTypeDTO> getSelectedTypeRows() {

		List<InstitutionTypeDTO> selectedItems = gridInstType
				.getSelectionModel().getSelectedItems();

		return selectedItems;
		
	}

	public List<InstitutionDTO> getSelectedInstitutionRows() {

		List<InstitutionDTO> selectedItems = gridInst
				.getSelectionModel().getSelectedItems();

		return selectedItems;
		
	}

	public List<TemplateDTO> getSelectedTemplateRows() {

		List<TemplateDTO> selectedItems = gridTemplate
				.getSelectionModel().getSelectedItems();

		return selectedItems;
		
	}

	public List<ReturnDTO> getSelectedReturnRows() {

		List<ReturnDTO> selectedItems = gridReturn
				.getSelectionModel().getSelectedItems();

		return selectedItems;
		
	}

	public Widget asWidget() {

		ToggleGroup toggleGroup = new ToggleGroup();
		toggleGroup.add(fiTypes);
		toggleGroup.add(fi);
		toggleGroup.add(templates);
		toggleGroup.add(returns);

		return this;
	}

}
