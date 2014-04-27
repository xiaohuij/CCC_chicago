package com.team6.twitterHarvester;

public class Auth {

	private String consumer;
	private String csecret;
	private String token;
	private String tsecret;
	
	//Consumer
	public String getConsumer(){
		return consumer;
	}
	public void setConsumer(String consumer){
		this.consumer = consumer;
	}
	
	//Consumer secret
	public String getCsecret(){
		return csecret;
	}
	public void setCsecret(String csecret){
		this.csecret = csecret;
	}
	
	//Token
	public String getToken(){
		return token;
	}
	public void setToken(String token){
		this.consumer = token;
	}
	
	//Token secret
	public String getTsecret(){
		return tsecret;
	}
	public void setTsecret(String tsecret){
		this.tsecret = tsecret;
	}
}
