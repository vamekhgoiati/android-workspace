package com.vamekh.client;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.vamekh.shared.Schedule;
import com.vamekh.shared.TemplateDTO;

public interface TemplateProperties extends PropertyAccess<TemplateDTO>{
	
	@Path("code")
	LabelProvider<TemplateDTO> codeLabel();
	
	ValueProvider<TemplateDTO, String> code();
	
	ValueProvider<TemplateDTO, String> description();
	
	ValueProvider<TemplateDTO, String> scheduleString();
	
}
