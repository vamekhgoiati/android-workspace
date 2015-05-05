package com.vamekh.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionTypeDTO;

@RemoteServiceRelativePath("institutionService")
public interface InstitutionService extends RemoteService{

	InstitutionDTO addInstitution(InstitutionDTO inst);
	
	boolean deleteInstitution(int id);
	
	boolean deleteInstitutions(List<InstitutionDTO> institutions);
	
	ArrayList<InstitutionDTO> getInstitutions();
	
	InstitutionDTO getInstitution(int id);
	
	InstitutionDTO updateInstitution(InstitutionDTO inst);
		
	boolean institutionCodeIsUnique(String code, int id);
	
	PagingLoadResult<InstitutionDTO> getInstitutions(
			PagingLoadConfig config);
	
	
	ArrayList<InstitutionDTO> checkInstitutions(List<InstitutionDTO> instDTOs);
	
}
