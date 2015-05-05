package com.vamekh.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionTypeDTO;

public interface InstitutionServiceAsync {

	void addInstitution(InstitutionDTO inst,
			AsyncCallback<InstitutionDTO> callback);

	void deleteInstitution(int id, AsyncCallback<Boolean> callback);

	void deleteInstitutions(List<InstitutionDTO> institutions,
			AsyncCallback<Boolean> callback);

	void getInstitution(int id, AsyncCallback<InstitutionDTO> callback);

	void getInstitutions(AsyncCallback<ArrayList<InstitutionDTO>> callback);

	void institutionCodeIsUnique(String code, int id,
			AsyncCallback<Boolean> callback);

	void updateInstitution(InstitutionDTO inst,
			AsyncCallback<InstitutionDTO> callback);

	void getInstitutions(PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<InstitutionDTO>> callback);

	void checkInstitutions(List<InstitutionDTO> instDTOs,
			AsyncCallback<ArrayList<InstitutionDTO>> callback);

}
