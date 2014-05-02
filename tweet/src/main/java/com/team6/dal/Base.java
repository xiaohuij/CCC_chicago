package com.team6.dal;

public class Base {
	
	private String _id;
	public void setId(String id){
		this._id = id;
	}
	public String getId(){
		return this._id;
	}
	
	private String _rev;
	public void setRev(String rev){
		this._rev = rev;
	}
	public String getRev(){
		return this._rev;
	}
	
	private int status;
	public void setStatus(int status){
		this.status = status;
	}
	public int getStatus(){
		return this.status;
	}
}
