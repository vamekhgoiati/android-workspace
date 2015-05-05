package com.vamekh.client;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionTypeDTO;


public interface InstitutionProperties extends PropertyAccess<InstitutionDTO>{

	@Path("code")
	LabelProvider<InstitutionDTO> codeLabel();
	
	ValueProvider<InstitutionDTO, String> code();
	
	ValueProvider<InstitutionDTO, String> name();
	
	ValueProvider<InstitutionDTO, String> address();
	
	ValueProvider<InstitutionDTO, String> phone();
	
	ValueProvider<InstitutionDTO, String> email();
	
	ValueProvider<InstitutionDTO, String> fax();
	
	ValueProvider<InstitutionDTO, Date> regDate();
	
	ValueProvider<InstitutionDTO, String> typeCode();
	
}
