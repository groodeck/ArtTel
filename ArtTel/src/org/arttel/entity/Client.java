package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Client")
public class Client {

	@Id
	@GeneratedValue
	@Column(name="ClientId")
	private Integer clientId; 
	
	@Column(name="ClientDesc")
	private String clientDesc; 
	
	@Column(name="ForInstalation")
	private Boolean forInstalation; 
	
	@Column(name="ForOrder")
	private Boolean forOrder; 
	
	@Column(name="ForReport")
	private Boolean forReport; 
	
	@Column(name="ForSqueeze")
	private Boolean forSqueeze; 
	
	@Column(name="ForDealing")
	private Boolean forDealing; 
	
	@Column(name="ForAgreement")
	private Boolean forAgreement; 
	
	@Column(name="ForInvoice")
	private Boolean forInvoice; 
	
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
	
	@Column(name="User")
	private String user;

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getClientDesc() {
		return clientDesc;
	}

	public void setClientDesc(String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public Boolean getForInstalation() {
		return forInstalation;
	}

	public void setForInstalation(Boolean forInstalation) {
		this.forInstalation = forInstalation;
	}

	public Boolean getForOrder() {
		return forOrder;
	}

	public void setForOrder(Boolean forOrder) {
		this.forOrder = forOrder;
	}

	public Boolean getForReport() {
		return forReport;
	}

	public void setForReport(Boolean forReport) {
		this.forReport = forReport;
	}

	public Boolean getForSqueeze() {
		return forSqueeze;
	}

	public void setForSqueeze(Boolean forSqueeze) {
		this.forSqueeze = forSqueeze;
	}

	public Boolean getForDealing() {
		return forDealing;
	}

	public void setForDealing(Boolean forDealing) {
		this.forDealing = forDealing;
	}

	public Boolean getForAgreement() {
		return forAgreement;
	}

	public void setForAgreement(Boolean forAgreement) {
		this.forAgreement = forAgreement;
	}

	public Boolean getForInvoice() {
		return forInvoice;
	}

	public void setForInvoice(Boolean forInvoice) {
		this.forInvoice = forInvoice;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}
