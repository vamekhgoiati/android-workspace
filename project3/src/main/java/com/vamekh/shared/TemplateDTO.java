package com.vamekh.shared;

import java.io.Serializable;

import org.apache.bcel.generic.GETSTATIC;

public class TemplateDTO implements Serializable{

	private int id;
	private String code;
	private String description;
	private Schedule schedule;
	private String scheduleString;
	
	public TemplateDTO(){ }
	
	public TemplateDTO(String code, String description, Schedule schedule){
		this(0, code, description, schedule);
	}
	
	public TemplateDTO(int id, String code, String description, Schedule schedule){
		this.id = id;
		this.code = code;
		this.description = description;
		this.schedule = schedule;
	}
	
	public int getId(){ return id; }
	public void setId(int id) { this.id = id; }
	public String getCode(){ return code; }
	public void setCode(String code){ this.code = code; }
	public String getDescription(){ return description; }
	public void setDescription(String description){ this.description = description; }
	public Schedule getSchedule(){ return schedule; }
	public void setSchedule(Schedule schedule){ this.schedule = schedule; }
	public String scheduleString(){ return getSchedule().toString(); };
	
	
}
