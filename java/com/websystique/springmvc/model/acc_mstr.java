package com.websystique.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="acc_mstr")
public class acc_mstr {

	
	@Id
	@Column(name="acc_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	
	
	
	
	/*
	 * -----------------------+-------------+------+-----+---------
ACC_ID	varchar(40)	NO	PRI
id	int(11)	YES	
ACC_TYPE_ID	int(11)	NO	
BALANCE	int(11)	NO	
DEBIT_CARD_NUM	int(11)	NO	
DEBIT_CARD_EXP_DATE	varchar(45)	NO	
DEBIT_CARD_CVV	int(11)	NO	
DEBIT_CARD_HOLDER_NAME	varchar(45)	YES	
ACC_STATUS	varchar(30)	YES	*/
	
	String acc_id ; 
	int id ; 
	int ACC_TYPE_ID  ; 
	int BALANCE   ;
	int DEBIT_CARD_NUM   ;
	String DEBIT_CARD_EXP_DATE ;
	int DEBIT_CARD_CVV    ;
	String DEBIT_CARD_HOLDER_NAME  ;
    String Acc_Status ;
	


	public String getAcc_Status() {
		return Acc_Status;
	}


	public void setAcc_Status(String acc_Status) {
		Acc_Status = acc_Status;
	}


	public String getAcc_id() {
		return acc_id;
	}


	public void setAcc_id(String acc_id) {
		this.acc_id = acc_id;
	}


	public int getid() {
		return id;
	}


	public void setid(int id) {
		this.id = id;
	}


	public int getACC_TYPE_ID() {
		return ACC_TYPE_ID;
	}


	public void setACC_TYPE_ID(int aCC_TYPE_ID) {
		ACC_TYPE_ID = aCC_TYPE_ID;
	}


	public int getBALANCE() {
		return BALANCE;
	}


	public void setBALANCE(int bALANCE) {
		BALANCE = bALANCE;
	}


	public int getDEBIT_CARD_NUM() {
		return DEBIT_CARD_NUM;
	}


	public void setDEBIT_CARD_NUM(int dEBIT_CARD_NUM) {
		DEBIT_CARD_NUM = dEBIT_CARD_NUM;
	}


	public String getDEBIT_CARD_EXP_DATE() {
		return DEBIT_CARD_EXP_DATE;
	}


	public void setDEBIT_CARD_EXP_DATE(String dEBIT_CARD_EXP_DATE) {
		DEBIT_CARD_EXP_DATE = dEBIT_CARD_EXP_DATE;
	}


	public int getDEBIT_CARD_CVV() {
		return DEBIT_CARD_CVV;
	}


	public void setDEBIT_CARD_CVV(int dEBIT_CARD_CVV) {
		DEBIT_CARD_CVV = dEBIT_CARD_CVV;
	}


	public String getDEBIT_CARD_HOLDER_NAME() {
		return DEBIT_CARD_HOLDER_NAME;
	}


	public void setDEBIT_CARD_HOLDER_NAME(String dEBIT_CARD_HOLDER_NAME) {
		DEBIT_CARD_HOLDER_NAME = dEBIT_CARD_HOLDER_NAME;
	}


	@Override
	public String toString() {
		return "acc_mstr [acc_id=" + acc_id + ", id=" + id + ", ACC_TYPE_ID=" + ACC_TYPE_ID + ", BALANCE=" + BALANCE
				+ ", DEBIT_CARD_NUM=" + DEBIT_CARD_NUM + ", DEBIT_CARD_EXP_DATE=" + DEBIT_CARD_EXP_DATE
				+ ", DEBIT_CARD_CVV=" + DEBIT_CARD_CVV + ", DEBIT_CARD_HOLDER_NAME=" + DEBIT_CARD_HOLDER_NAME
				+ ", Acc_Status=" + Acc_Status + "]";
	}


	/*@Override
	public String toString() {
		return "Acc_mstr [Acc_id=" +acc_id + ", Balance=" + BALANCE + ", id=" + id +  "]";

	}*/
	
}
