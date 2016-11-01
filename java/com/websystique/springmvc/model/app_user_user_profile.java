package com.websystique.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="app_user_user_profile")
public class app_user_user_profile {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	int user_id;
	int user_profile_id ;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getUser_profile_id() {
		return user_profile_id;
	}
	public void setUser_profile_id(int user_profile_id) {
		this.user_profile_id = user_profile_id;
	}
	
	@Override
	public String toString() {
		return "app_user_user_profile [user_id=" + user_id + ", user_profile_id=" + user_profile_id + "]";
	}

}
