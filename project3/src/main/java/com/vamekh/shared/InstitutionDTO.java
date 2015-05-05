package com.vamekh.shared;

import java.io.Serializable;
import java.util.Date;

public class InstitutionDTO implements Serializable{
	
	private int id;
	
	private String code;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String fax;
	private InstitutionTypeDTO type;
	private Date regDate;
	
	public InstitutionDTO(){}
	
	public InstitutionDTO(String code, String name, String address, String phone, String email, String fax, InstitutionTypeDTO type, Date regDate){
		this(0, code,  name,  address,  phone,  email,  fax,  type,  regDate);
	}
	
	public InstitutionDTO(int id, String code, String name, String address, String phone, String email, String fax, InstitutionTypeDTO type, Date regDate){
		this.id = id;
		this.code = code;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.fax = fax;
		this.type = type;
		this.regDate = regDate;
	}
	
	public void setId(int id){ this.id = id; }
	
	public int getId(){	return this.id;	}
	
	public void setCode(String code){ this.code = code;	}
	
	public String getCode(){ return this.code; }
	
	public void setName(String name){ this.name = name;	}
	
	public String getName(){ return this.name; }
	
	public void setAddress(String address){	this.address = address; }
	
	public String getAddress(){	return this.address; }
	
	public void setPhone(String phone){ this.phone = phone; }
	
	public String getPhone(){ return this.phone; }
	
	public void setEmail(String email){	this.email = email; }
	
	public String getEmail(){ return this.email; }
	
	public void setFax(String fax){	this.fax = fax; }
	
	public String getFax(){	return this.fax; }
	
	public void setType(InstitutionTypeDTO type){ this.type = type; }
	
	public InstitutionTypeDTO getType(){ return this.type; }
	
	public void setRegDate(Date regDate){ this.regDate = regDate; }
	
	public Date getRegDate(){ return this.regDate; }
	
	public String getTypeCode(){ return getType().getCode(); }
	
}
