package com.vamekh.shared;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="Return")
@Table(name="RETURNS")
public class Return {
	
	@Id
	@GeneratedValue
	int id;
	
	String code;
	String description;
	
	@ManyToOne
	@JoinColumn(name="FI_ID")
	Institution institution;
	
	@ManyToOne
	@JoinColumn(name="TEMPLATE_ID")
	Template template;
	
	public Return(){}
	
	public Return(String code, String description, Institution institution, Template template){
		this(0, code, description, institution, template);
	}
	
	public Return(int id, String code, String description, Institution institution, Template template){
		
		this.id = id;
		this.code = code;
		this.description = description;
		this.institution = institution;
		this.template = template;
		
	}
	
	public Return(ReturnDTO retDTO){
		
		this.id = retDTO.getId();
		this.code = retDTO.getCode();
		this.description = retDTO.getDescription();
		this.institution = new Institution(retDTO.getInstitution());
		this.template = new Template(retDTO.getTemplate());
		
	}
	
	public int getId(){ return id; }
	
	public void setId(int id){ this.id = id; }
	
	public String getCode(){ return code; }
	
	public void setCode(String code){ this.code = code; }
	
	public String getDescription(){ return description; }
	
	public void setDescription(String description){ this.description = description; }
	
	public Institution getInstitution(){ return institution; }
	
	public void setInstitution(Institution institution){ this.institution = institution; }

	public Template getTemplate(){ return template; }
	
	public void setTemplate(Template template){ this.template = template; }
	
}
