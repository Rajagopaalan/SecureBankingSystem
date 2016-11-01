package com.websystique.springmvc.model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="transc_otp_mstr")
public class Otp implements Serializable{

	

	
	
	@Id
	@Column(name="ACC_ID", nullable=false)
	private long accountId;
		

	@Column(name="PHONE_NUM", nullable=false)
	private String phone;

	
	@Column(name="OTP_NUM", nullable=false)
	private String otp;


	
	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	

	@Override
	public String toString() {
		return "Otp [accountId=" + accountId + ", phone=" + phone + ", otp="
				+ otp + "]";
	}

		
	


	/*
	 * DO-NOT-INCLUDE passwords in toString function.
	 * It is done here just for convenience purpose.
	 */


	
}