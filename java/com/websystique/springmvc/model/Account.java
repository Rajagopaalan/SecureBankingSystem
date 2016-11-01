package com.websystique.springmvc.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACC_MSTR")
public class Account {
	
	/* 
  `ACC_ID` INT NOT NULL,
  `EXT_USER_ID` INT NOT NULL,
  `ACC_TYPE_ID` INT NULL,
  `BALANCE` INT NULL,
  `DEBIT_CARD_NUM` INT NOT NULL,
  `DEBIT_CARD_EXP_DATE` VARCHAR(45) NOT NULL,
  `DEBIT_CARD_CVV` INT NOT NULL,
  `DEBIT_CARD_HOLDER_NAME` VARCHAR(45) NULL,
   ACC_STATUS VARCHAR
  
  
	 * 
	 * */
	@Id
	@Column(name="ACC_ID")
	private int account_no;
	@Column(name="id")
	private int uid;
	@Column(name="ACC_TYPE_ID")
	private int account_type_id;
	@Column(name="BALANCE")
	private double balance;
	@Column(name="DEBIT_CARD_NUM")
	private int dc_no;
	@Column(name="DEBIT_CARD_EXP_DATE")
	private String debit_exp_date;
	@Column(name="DEBIT_CARD_CVV")
	private int cvv;
	@Column(name="DEBIT_CARD_HOLDER_NAME")
	private String card_holder_name;
	@Column(name="ACC_STATUS")
	private String status;
	
	
	public int getAccount_no() {
		return account_no;
	}

	public void setAccount_no(int account_no) {
		this.account_no = account_no;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getAccount_type_id() {
		return account_type_id;
	}

	public void setAccount_type_id(int account_type_id) {
		this.account_type_id = account_type_id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	
	public int getDc_no() {
		return dc_no;
	}

	public void setDc_no(int dc_no) {
		this.dc_no = dc_no;
	}

	public String getDebit_exp_date() {
		return debit_exp_date;
	}

	public void setDebit_exp_date(String debit_exp_date) {
		this.debit_exp_date = debit_exp_date;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getCard_holder_name() {
		return card_holder_name;
	}

	public void setCard_holder_name(String card_holder_name) {
		this.card_holder_name = card_holder_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Account [account_no=" + account_no + ", uid=" + uid + ", account_type_id=" + account_type_id
				+ ", balance=" + balance + ", dc_no=" + dc_no + ", debit_exp_date=" + debit_exp_date + ", cvv=" + cvv
				+ ", card_holder_name=" + card_holder_name + ", status=" + status + "]";
	}
	

}