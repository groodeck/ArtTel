package org.arttel.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Seller")
public class Seller {

	@Id
	@GeneratedValue
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

	@OneToMany
	@JoinColumn(name="sellerId")
	private List<SellerBankAccount> bankAccounts;

	@Column(name="GenerateDealingOnInvoiceSettle")
	private Boolean generateDealingOnInvoiceSettle;

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAppartment() {
		return appartment;
	}

	public List<SellerBankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public String getBankName() {
		return bankName;
	}

	public String getCity() {
		return city;
	}

	public Boolean getGenerateDealingOnInvoiceSettle() {
		return generateDealingOnInvoiceSettle;
	}

	public String getHouse() {
		return house;
	}

	public String getNip() {
		return nip;
	}

	public String getSellerDesc() {
		return sellerDesc;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public String getSellerIdn() {
		return sellerIdn;
	}

	public String getStreet() {
		return street;
	}

	public String getUser() {
		return user;
	}

	public String getZip() {
		return zip;
	}

	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAppartment(final String appartment) {
		this.appartment = appartment;
	}

	public void setBankAccounts(final List<SellerBankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setGenerateDealingOnInvoiceSettle(
			final Boolean generateDealingOnInvoiceSettle) {
		this.generateDealingOnInvoiceSettle = generateDealingOnInvoiceSettle;
	}

	public void setHouse(final String house) {
		this.house = house;
	}

	public void setNip(final String nip) {
		this.nip = nip;
	}

	public void setSellerDesc(final String sellerDesc) {
		this.sellerDesc = sellerDesc;
	}

	public void setSellerId(final Integer sellerId) {
		this.sellerId = sellerId;
	}

	public void setSellerIdn(final String sellerIdn) {
		this.sellerIdn = sellerIdn;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public void setZip(final String zip) {
		this.zip = zip;
	}
}
