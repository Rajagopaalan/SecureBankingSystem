package com.websystique.springmvc.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CREDIT_CARD_TRANSC")
public class CreditCardTransc {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="credit_card_transc_id")
	private int ccTranscId;
	@Column(name="credit_card_id")
	private long ccId;
	@Column(name="id")
	private int id;
	@Column(name="acc_id")
	private int acc_id;
	@Column(name="amount")
	private double amount;
	@Column(name="merchant")
	private int merchant;
	@Column(name="transc_date")
	private String date;
	@Column(name="transc_desc")
	private String trxDesc;
	public int getCcTranscId() {
		return ccTranscId;
	}
	public void setCcTranscId(int ccTranscId) {
		this.ccTranscId = ccTranscId;
	}
	public long getCcId() {
		return ccId;
	}
	public void setCcId(long l) {
		this.ccId = l;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(int acc_id) {
		this.acc_id = acc_id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getMerchant() {
		return merchant;
	}
	public void setMerchant(int merchant) {
		this.merchant = merchant;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTrxDesc() {
		return trxDesc;
	}
	public void setTrxDesc(String trxDesc) {
		this.trxDesc = trxDesc;
	}
	@Override
	public String toString() {
		return "CreditCardTransc [ccTranscId=" + ccTranscId + ", ccId=" + ccId + ", id=" + id + ", acc_id=" + acc_id
				+ ", amount=" + amount + ", merchant=" + merchant + ", date=" + date + ", trxDesc=" + trxDesc + "]";
	}
	
	
}
