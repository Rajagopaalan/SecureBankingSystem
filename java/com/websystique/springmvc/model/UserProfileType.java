package com.websystique.springmvc.model;

import java.io.Serializable;

public enum UserProfileType implements Serializable {
	ADMIN("ADMIN"), 
	MANAGER("MANAGER"), 
	USER("USER"), 
	REGEMPLOYEE("REGEMPLOYEE"), 
	MERCHANT("MERCHANT") ;

	String userProfileType;
	
	private UserProfileType(String userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public String getUserProfileType(){
		return userProfileType;
	}
	
}
