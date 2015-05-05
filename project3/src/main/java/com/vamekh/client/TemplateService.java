package com.vamekh.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.TemplateDTO;

@RemoteServiceRelativePath("templateService")
public interface TemplateService extends RemoteService{
	
	TemplateDTO addTemplate(TemplateDTO templateDTO);
	
	boolean deleteTemplate(int id);
	
	boolean deleteTemplates(List<TemplateDTO> templates);
	
	ArrayList<TemplateDTO> getTemplates();
	
	TemplateDTO getTemplate(int id);
	
	TemplateDTO updateTemplate(TemplateDTO templateDTO);
	
	PagingLoadResult<TemplateDTO> getTemplates(
			PagingLoadConfig config);
	
	boolean codeIsUnique(String code, int id);
	
	ArrayList<TemplateDTO> checkTemplates(List<TemplateDTO> templateDTOs);
	
}
