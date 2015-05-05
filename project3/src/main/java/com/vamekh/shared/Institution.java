package com.vamekh.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="Institution")
@Table(name="FI")
public class Institution implements Serializable{

	@Id
	@GeneratedValue
	private int id;
	
	private String code;
	private String name;
	private String address;
	
	@Column(name="TELEPHONE")
	private String phone;
	private String email;
	private String fax;
	
	@ManyToOne
	@JoinColumn(name="FI_TYPE_ID")
	private InstitutionType type;
	
	@Column(name="REG_DATE")
	private Date regDate;
	
	public Institution(){}
	
	public Institution(String code, String name, String address, String phone, String email, String fax, InstitutionType type, Date regDate){
		this(0, code,  name,  address,  phone,  email,  fax,  type,  regDate);
	}
	
	public Institution(int id, String code, String name, String address, String phone, String email, String fax, InstitutionType type, Date regDate){
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
	
	public Institution(InstitutionDTO institutionDTO){
		this.id = institutionDTO.getId();
		this.code = institutionDTO.getCode();
		this.name = institutionDTO.getName();
		this.address = institutionDTO.getAddress();
		this.phone = institutionDTO.getPhone();
		this.email = institutionDTO.getEmail();
		this.fax = institutionDTO.getFax();
		this.type = new InstitutionType(institutionDTO.getType());
		this.regDate = institutionDTO.getRegDate();
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
	
	public void setType(InstitutionType type){ this.type = type; }
	
	public InstitutionType getType(){ return this.type; }
	
	public void setRegDate(Date regDate){ this.regDate = regDate; }
	
	public Date getRegDate(){ return this.regDate; }
	
}
