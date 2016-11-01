package com.websystique.springmvc.model;
import java.sql.Date;

import java.util.*;

import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity

@Table(name="TRANSC_MSTR")
public class Transc_mstr {
	@Id
	@Column(name="Transc_Id")
	int transc_id ;
	@ColumnDefault("10")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
   

	@Column(name="TRANSC_ACC_NUM")
	int Transc_acc_num ;
	@Column(name="TRANSC_AMT")
	int transc_amt ;
	@Column(name="TRASC_DATE")
    String Trasc_date ;
	@Column(name="TRANSC_TYPE_ID")
	int transc_type_id;
	@Column(name="TRANSC_MERCHANT")
	String transc_merchant ;
	@Column(name="TRANSC_FEE")
	int  transc_fee ;
	@Column(name="TRANSC_DESC")
	String transc_desc; 
	@Column(name="TRANSC_STATUS_ID")
	int transc_status_Id ;
	
	@Column(name="BALANCE")
	int Balance ;
	@Column(name="id")
	int id ;
	
/*
 * 
 * TRANSC_ID
TRANSC_ACC_NUM
TRANSC_AMT
id
TRASC_DATE
TRANSC_TYPE_ID
TRANSC_MERCHANT
TRANSC_FEE
TRANSC_DESC
TRANSC_STATUS_ID
BALANCE
 * 
 * */
	
	



	public int getTransc_acc_num() {
		return Transc_acc_num;
	}



	public void setTransc_acc_num(int transc_acc_num) {
		Transc_acc_num = transc_acc_num;
	}



	public int getTransc_amt() {
		return transc_amt;
	}



	public void setTransc_amt(int transc_amt) {
		this.transc_amt = transc_amt;
	}



	public String getTrasc_date() {
		return Trasc_date;
	}



	public void setTrasc_date(String trasc_date) {
		Trasc_date = trasc_date;
	}



	public int getTransc_type_id() {
		return transc_type_id;
	}



	public void setTransc_type_id(int transc_type_id) {
		this.transc_type_id = transc_type_id;
	}



	public String getTransc_merchant() {
		return transc_merchant;
	}



	public void setTransc_merchant(String transc_merchant) {
		this.transc_merchant = transc_merchant;
	}



	public int getTransc_fee() {
		return transc_fee;
	}



	public void setTransc_fee(int transc_fee) {
		this.transc_fee = transc_fee;
	}



	public String getTransc_desc() {
		return transc_desc;
	}



	public void setTransc_desc(String transc_desc) {
		this.transc_desc = transc_desc;
	}



	public int getTransc_status_Id() {
		return transc_status_Id;
	}



	public void setTransc_status_Id(int transc_status_Id) {
		this.transc_status_Id = transc_status_Id;
	}



	public int getBalance() {
		return Balance;
	}



	public void setBalance(int balance) {
		Balance = balance;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getTransc_id() {
		return transc_id;
	}



	public void setTransc_id(int transc_id) {
		this.transc_id = transc_id;
	}



	@Override
	public String toString() {
		return "Transc_mstr [Transc_acc_num=" + Transc_acc_num + ", transc_amt=" + transc_amt + ", Trasc_date="
				+ Trasc_date + ", transc_type_id=" + transc_type_id + ", transc_merchant=" + transc_merchant
				+ ", transc_fee=" + transc_fee + ", transc_desc=" + transc_desc + ", transc_status_Id="
				+ transc_status_Id + ", Balance=" + Balance + ", id=" + id + ", transc_id=" + transc_id + "]";
	}
	


	
	/*@Override
	public String toString() {
		return "Transc_mstr [Transc_acc_num=" + Transc_acc_num + ", transc_amt=" + transc_amt + ", Transc_date=" + Trasc_date +  "]";
	}*/
	
	
}
