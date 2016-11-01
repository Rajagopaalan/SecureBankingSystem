package com.websystique.springmvc.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TRANSC_MSTR")
public class Transaction {
	
	@Id
	@Column(name="transc_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int transactionId;
	@Column(name="transc_acc_num")
	private int trxAccNum;
	@Column(name="transc_amt")
	private double trxAmount;
	@Column(name="id")
	private int id;
	@Column(name="trasc_date")
	private String trxDate;
	@Column(name="transc_type_id")
	private int trxTypeId;
	@Column(name="transc_merchant")
	private String trxMerchant;
	@Column(name="transc_fee")
	private double trxFee;
	@Column(name="transc_desc")
	private String trxDesc;
	@Column(name="transc_status_id")
	private int trxStatusId;
	@Column(name="balance")
	private double balance;
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getTrxAccNum() {
		return trxAccNum;
	}
	public void setTrxAccNum(int trxAccNum) {
		this.trxAccNum = trxAccNum;
	}
	public double getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(double trxAmount) {
		this.trxAmount = trxAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}
	public int getTrxTypeId() {
		return trxTypeId;
	}
	public void setTrxTypeId(int trxTypeId) {
		this.trxTypeId = trxTypeId;
	}
	public String getTrxMerchant() {
		return trxMerchant;
	}
	public void setTrxMerchant(String trxMerchant) {
		this.trxMerchant = trxMerchant;
	}
	public double getTrxFee() {
		return trxFee;
	}
	public void setTrxFee(double trxFee) {
		this.trxFee = trxFee;
	}
	public String getTrxDesc() {
		return trxDesc;
	}
	public void setTrxDesc(String trxDesc) {
		this.trxDesc = trxDesc;
	}
	public int getTrxStatusId() {
		return trxStatusId;
	}
	public void setTrxStatusId(int trxStatusId) {
		this.trxStatusId = trxStatusId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", trxAccNum=" + trxAccNum + ", trxAmount=" + trxAmount
				+ ", id=" + id + ", trxDate=" + trxDate + ", trxTypeId=" + trxTypeId + ", trxMerchant=" + trxMerchant
				+ ", trxFee=" + trxFee + ", trxDesc=" + trxDesc + ", trxStatusId=" + trxStatusId + ", balance="
				+ balance + "]";
	}
	
}
