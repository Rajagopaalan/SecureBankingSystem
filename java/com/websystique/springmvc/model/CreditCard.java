package com.websystique.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CREDIT_CARD_MSTR")
public class CreditCard {
	
	@Id
	@Column(name="CREDIT_CARD_ID")
	private long cardId;
	@Column(name="ID")
	private int uid;
	@Column(name="ACC_ID")
	private int accountId;
	@Column(name="CARDHOLDER_NAME")
	private String chName;
	@Column(name="EXP_DATE")
	private String expDate;
	@Column(name="CREDIT_CARD_CVV")
	private int cvv;
	@Column(name="CREDIT_CARD_LIMIT")
	private int creditLimit;
	@Column(name="USER_SSN")
	private String snn;
	@Column(name="AMOUNT_SPENT")
	private double amountSpent;
	@Column(name="DUE_DATE")
	private String dueDate;
	@Column(name="LAST_LIMIT_UPDATE")
	private String lastLimitUpdate;
	@Column(name="INTEREST_AMOUNT")
	private double interestAmount;
	@Column(name="LATE_FEE")
	private double lateFee;
	public long getCardId() {
		return cardId;
	}
	public void setCardId(long cardId) {
		this.cardId = cardId;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public int getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getSnn() {
		return snn;
	}
	public void setSnn(String snn) {
		this.snn = snn;
	}
	public double getAmountSpent() {
		return amountSpent;
	}
	public void setAmountSpent(double amountSpent) {
		this.amountSpent = amountSpent;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getLastLimitUpdate() {
		return lastLimitUpdate;
	}
	public void setLastLimitUpdate(String lastLimitUpdate) {
		this.lastLimitUpdate = lastLimitUpdate;
	}
	public double getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}
	public double getLateFee() {
		return lateFee;
	}
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}
	
	@Override
	public String toString() {
		return "CreditCard [cardId=" + cardId + ", uid=" + uid + ", accountId=" + accountId + ", chName=" + chName
				+ ", expDate=" + expDate + ", cvv=" + cvv + ", creditLimit=" + creditLimit + ", snn=" + snn
				+ ", amountSpent=" + amountSpent + ", dueDate=" + dueDate + ", lastLimitUpdate=" + lastLimitUpdate
				+ ", interestAmount=" + interestAmount + ", lateFee=" + lateFee + "]";
	}
	
	
}
