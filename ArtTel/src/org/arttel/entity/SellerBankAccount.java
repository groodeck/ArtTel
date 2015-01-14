package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SellerBankAccount")
public class SellerBankAccount {

	@Id
	@GeneratedValue
	@Column(name = "SellerBankAccountId")
	private Integer sellerBankAccountId;
	
	@Column(name = "SellerId")
	private Integer sellerId;
	
	
	@Column(name = "BankName")
	private String bankName;
	
	@Column(name = "AccountNumber")
	private String accountNumber;

	public Integer getSellerBankAccountId() {
		return sellerBankAccountId;
	}

	public void setSellerBankAccountId(Integer sellerBankAccountId) {
		this.sellerBankAccountId = sellerBankAccountId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
