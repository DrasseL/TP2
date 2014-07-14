package com.doram;

public class Faction {
	private int id;
	private String name;
	private String description;
	
	public Faction(){}
	
	public Faction(String name, String description){
		super();
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString(){
		return "Faction [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

	public Integer getId(){
		return this.id;
	}
	
	public void setId(Integer value){
		this.id = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String value){
		this.name = value;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String value){
		this.description = value;
	}
}
