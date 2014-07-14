package com.doram;

public class User {
	private int id;
	private String username;
	private String password;
	private int fkfaction;
	private int fkrank;
	
	public User(){}
	
	public User(String username, String password, int fkfaction, int fkrank ){
		super();
		this.username = username;
		this.password = password;
		this.fkfaction = fkfaction;
		this.fkrank = fkrank;
	}
	
	@Override
	public String toString(){
		return "User [id=" + id + ",username=" + username + ", password=" + password + ", fkfaction=" + fkfaction +", fkrank=" + fkrank + "]";
	}
	
	public Integer getId(){
		return this.id;
	}
	
	public void setId(int value){
		this.id = value;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public void setUsername(String value){
		this.username = value;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String value){
		this.password = value;
	}
	
	public int getFkFaction(){
		return this.fkfaction;
	}
	
	public void setFkFaction(int value){
		this.fkfaction = value;
	}
	
	public int getFkRank(){
		return this.fkrank;
	}
	
	public void setFkRank(int value){
		this.fkrank = value;
	}
	
	
}
