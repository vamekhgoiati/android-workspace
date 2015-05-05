package com.vamekh.shared;

import java.io.Serializable;

public class ReturnDTO implements Serializable{

	int id;
	
	private String code;
	private String description;
	private InstitutionDTO institution;
	private TemplateDTO template;
	private String institutionCode;
	private String templateCode;
	
	public ReturnDTO(){}
	
	public ReturnDTO(String code, String description, InstitutionDTO institution, TemplateDTO template){
		this(0, code, description, institution, template);
	}
	
	public ReturnDTO(int id, String code, String description, InstitutionDTO institution, TemplateDTO template){
		
		this.id = id;
		this.code = code;
		this.description = description;
		this.institution = institution;
		this.template = template;
		
	}
	
	public int getId(){ return id; }
	
	public void setId(int id){ this.id = id; }
	
	public String getCode(){ return code; }
	
	public void setCode(String code){ this.code = code; }
	
	public String getDescription(){ return description; }
	
	public void setDescription(String description){ this.description = description; }
	
	public InstitutionDTO getInstitution(){ return institution; }
	
	public void setInstitution(InstitutionDTO institution){ this.institution = institution; }
	
	public TemplateDTO getTemplate(){ return template; }
	
	public void setTemplate(TemplateDTO template){ this.template = template; }
	
	public String getInstitutionCode(){ return getInstitution().getCode(); }
	
	public String getTemplateCode(){ return getTemplate().getCode(); }
	
}
