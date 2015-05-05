package com.vamekh.shared;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;

@Entity(name="Template")
@Table(name="TEMPLATES")
public class Template implements Serializable{

	@Id
	@GeneratedValue
	private int id;
	
	private String code;
	private String description;
	
	@Column(name="Schedule")
	@Enumerated(EnumType.ORDINAL)
	private Schedule schedule;
	
	public Template(){ }
	
	public Template(String code, String description, Schedule schedule){
		this(0, code, description, schedule);
	}
	
	public Template(int id, String code, String description, Schedule schedule){
		this.id = id;
		this.code = code;
		this.description = description;
		this.schedule = schedule;
	}
	
	public Template(TemplateDTO templateDTO){
		this.id = templateDTO.getId();
		this.code = templateDTO.getCode();
		this.description = templateDTO.getDescription();
		this.schedule = templateDTO.getSchedule();
	}
	
	public int getId(){ return id; }
	
	public void setId(int id) { this.id = id; }
	
	public String getCode(){ return code; }
	
	public void setCode(String code){ this.code = code; }
	
	public String getDescription(){ return description; }
	
	public void setDescription(String description){ this.description = description; }
	
	public Schedule getSchedule(){ return schedule; }
	
	public void setSchedule(Schedule schedule){ this.schedule = schedule; }
	
	
}
