package com.vamekh.shared;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="InstitutionType")
@Table(name="FI_TYPES")
public class InstitutionType implements Serializable{
	
	@Id
	@GeneratedValue
	private int id;
	
	private String code;
	private String name;
	
	public InstitutionType(){};
	
	public InstitutionType(String code, String name){
		this(0, code, name);
	}
	
	public InstitutionType(int id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	public InstitutionType(InstitutionTypeDTO instTypeDTO){
		this.id = instTypeDTO.getId();
		this.code = instTypeDTO.getCode();
		this.name = instTypeDTO.getName();
	}
	
	public void setId(int id){ this.id = id; }
	
	public int getId(){	return this.id; }
	
	public void setCode(String code){ this.code = code; }
	
	public String getCode(){ return this.code; }
	
	public void setName(String name){ this.name = name; }
	
	public String getName(){ return this.name; }
	
}
