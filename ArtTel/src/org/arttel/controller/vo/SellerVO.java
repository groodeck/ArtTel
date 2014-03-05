package org.arttel.controller.vo;

import org.arttel.view.ComboElement;

public class SellerVO implements ComboElement, InvoiceParticipant {
	
	private String sellerId;
	private String sellerDesc;

	private String nip;
	
	private String city;
	private String street;
	private String house;
	private String appartment;
	private String zip;
	
	private String bankName;
 	private String accountNumber;                                                                                           				
	
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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
	@Override
	public String getDesc() {
		return getSellerDesc();
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
	@Override
	public String getIdn() {
		return getSellerId();
	}
}
