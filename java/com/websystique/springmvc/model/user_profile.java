package com.websystique.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="USER_PROFILE")
public class user_profile {
	@Override
	public String toString() {
		return "user_profile [id=" + id + ", type=" + type + "]";
	}
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	int id ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	String type;


}
