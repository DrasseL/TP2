package com.doram;

public class Rank {
	private int id;
	private String name;
	
	public Rank(){}
	
	public Rank(String name){
		super();
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "Rank [id=" + id + ",name=" + name + "]";
	}
	
	public Integer getId(){
		return this.id;
	}
	
	public void setId(int value){
		this.id = value;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String value){
		this.name = value;
	}
}
