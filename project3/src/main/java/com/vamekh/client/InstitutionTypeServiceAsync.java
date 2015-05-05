package com.vamekh.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.vamekh.shared.InstitutionTypeDTO;

public interface InstitutionTypeServiceAsync {

	void addInstitutionType(InstitutionTypeDTO type,
			AsyncCallback<InstitutionTypeDTO> callback);

	void deleteInstitutionType(int id, AsyncCallback<Boolean> callback);

	void deleteInstitutionTypes(List<InstitutionTypeDTO> types,
			AsyncCallback<Boolean> callback);

	void getInstitutionType(int id, AsyncCallback<InstitutionTypeDTO> callback);

	void getInstitutionTypes(
			AsyncCallback<ArrayList<InstitutionTypeDTO>> callback);

	void isTypeUnique(String code, int id, AsyncCallback<Boolean> callback);

	void updateInstitutionType(InstitutionTypeDTO type,
			AsyncCallback<InstitutionTypeDTO> callback);

	void getInstitutionTypes(
			PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<InstitutionTypeDTO>> callback);

	void checkTypes(List<InstitutionTypeDTO> typeDTOs,
			AsyncCallback<ArrayList<InstitutionTypeDTO>> asyncCallback);

}
