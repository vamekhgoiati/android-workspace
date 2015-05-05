package com.vamekh.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.vamekh.shared.InstitutionTypeDTO;

@RemoteServiceRelativePath("typeService")
public interface InstitutionTypeService extends RemoteService{
	
	InstitutionTypeDTO addInstitutionType(InstitutionTypeDTO type);
	
	boolean deleteInstitutionType(int id);
	
	boolean deleteInstitutionTypes(List<InstitutionTypeDTO> types);
	
	ArrayList<InstitutionTypeDTO> getInstitutionTypes();
	
	PagingLoadResult<InstitutionTypeDTO> getInstitutionTypes(
			PagingLoadConfig config);
	
	InstitutionTypeDTO getInstitutionType(int id);
	
	InstitutionTypeDTO updateInstitutionType(InstitutionTypeDTO type);
	
	boolean isTypeUnique(String code, int id);
	
	ArrayList<InstitutionTypeDTO> checkTypes(List<InstitutionTypeDTO> typeDTOs);
	
}
