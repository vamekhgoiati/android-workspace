package com.vamekh.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.vamekh.shared.ReturnDTO;

public interface ReturnServiceAsync {

	void addReturn(ReturnDTO retDTO, AsyncCallback<ReturnDTO> callback);

	void deleteReturn(int id, AsyncCallback<Boolean> callback);

	void deleteReturns(List<ReturnDTO> returns, AsyncCallback<Boolean> callback);

	void getReturn(int id, AsyncCallback<ReturnDTO> callback);

	void getReturns(AsyncCallback<ArrayList<ReturnDTO>> callback);

	void isReturnCodeUnique(String code, int id, AsyncCallback<Boolean> callback);

	void updateReturn(ReturnDTO retDTO, AsyncCallback<ReturnDTO> callback);

	void getReturns(PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<ReturnDTO>> callback);

}
