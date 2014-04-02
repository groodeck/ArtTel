package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Seller")
public class Seller {

	@Id
	@Column(name="SellerId")
	private Integer sellerId;

	@Column(name="SellerIdn")
	private String sellerIdn;
	
	@Column(name="SellerDesc")
	private String sellerDesc;
	
	@Column(name="Nip")
	private String nip;
	
	@Column(name="City")
	private String city;
	
	@Column(name="Street")
	private String street;
	
	@Column(name="House")
	private String house;
	
	@Column(name="Appartment")
	private String appartment;
	
	@Column(name="Zip")
	private String zip;
	
	@Column(name="BankName")
	private String bankName;
	
	@Column(name="AccountNumber")
	private String accountNumber;
	
	@Column(name="User")
	private String user;
	
	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerIdn() {
		return sellerIdn;
	}

	public void setSellerIdn(String sellerIdn) {
		this.sellerIdn = sellerIdn;
	}

	public String getSellerDesc() {
		return sellerDesc;
	}

	public void setSellerDesc(String sellerDesc) {
		this.sellerDesc = sellerDesc;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getAppartment() {
		return appartment;
	}

	public void setAppartment(String appartment) {
		this.appartment = appartment;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
