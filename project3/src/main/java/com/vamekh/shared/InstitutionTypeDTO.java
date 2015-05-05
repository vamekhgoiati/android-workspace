package com.vamekh.shared;

import java.io.Serializable;

public class InstitutionTypeDTO implements Serializable{

	private int id;
	private String code;
	private String name;
	
	public InstitutionTypeDTO(){};
	
	public InstitutionTypeDTO(String code, String name){
		this(0, code, name);
	}
	
	public InstitutionTypeDTO(int id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	public void setId(int id){ this.id = id; }
	
	public int getId(){	return this.id; }
	
	public void setCode(String code){ this.code = code; }
	
	public String getCode(){ return this.code; }
	
	public void setName(String name){ this.name = name; }
	
	public String getName(){ return this.name; }
	
}
