package com.vamekh.client;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.vamekh.shared.InstitutionTypeDTO;

public interface TypeProperties extends PropertyAccess<InstitutionTypeDTO>{

	@Path("code")
	LabelProvider<InstitutionTypeDTO> codeLabel();
	
	ValueProvider<InstitutionTypeDTO, String> name();
	
	ValueProvider<InstitutionTypeDTO, String> code();
	
}
