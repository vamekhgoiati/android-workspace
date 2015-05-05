package com.vamekh.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.vamekh.shared.TemplateDTO;

public interface TemplateServiceAsync {

	void addTemplate(TemplateDTO templateDTO,
			AsyncCallback<TemplateDTO> callback);

	void deleteTemplate(int id, AsyncCallback<Boolean> callback);

	void deleteTemplates(List<TemplateDTO> templates, AsyncCallback<Boolean> callback);

	void getTemplates(AsyncCallback<ArrayList<TemplateDTO>> callback);

	void getTemplate(int id, AsyncCallback<TemplateDTO> callback);

	void updateTemplate(TemplateDTO templateDTO,
			AsyncCallback<TemplateDTO> callback);

	void codeIsUnique(String code, int id, AsyncCallback<Boolean> callback);

	void getTemplates(PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<TemplateDTO>> callback);

	void checkTemplates(List<TemplateDTO> templateDTOs,
			AsyncCallback<ArrayList<TemplateDTO>> callback);

}
