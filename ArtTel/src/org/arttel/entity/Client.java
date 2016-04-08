package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Client")
public class Client implements UserSet {

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

	public String getAppartment() {
		return appartment;
	}

	public String getCity() {
		return city;
	}

	public String getClientDesc() {
		return clientDesc;
	}

	public Integer getClientId() {
		return clientId;
	}

	public Boolean getForAgreement() {
		return forAgreement;
	}

	public Boolean getForDealing() {
		return forDealing;
	}

	public Boolean getForInstalation() {
		return forInstalation;
	}

	public Boolean getForInvoice() {
		return forInvoice;
	}

	public Boolean getForOrder() {
		return forOrder;
	}

	public Boolean getForReport() {
		return forReport;
	}

	public Boolean getForSqueeze() {
		return forSqueeze;
	}

	public String getHouse() {
		return house;
	}

	public String getNip() {
		return nip;
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

	public void setAppartment(final String appartment) {
		this.appartment = appartment;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setClientDesc(final String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public void setClientId(final Integer clientId) {
		this.clientId = clientId;
	}

	public void setForAgreement(final Boolean forAgreement) {
		this.forAgreement = forAgreement;
	}

	public void setForDealing(final Boolean forDealing) {
		this.forDealing = forDealing;
	}

	public void setForInstalation(final Boolean forInstalation) {
		this.forInstalation = forInstalation;
	}

	public void setForInvoice(final Boolean forInvoice) {
		this.forInvoice = forInvoice;
	}

	public void setForOrder(final Boolean forOrder) {
		this.forOrder = forOrder;
	}

	public void setForReport(final Boolean forReport) {
		this.forReport = forReport;
	}

	public void setForSqueeze(final Boolean forSqueeze) {
		this.forSqueeze = forSqueeze;
	}

	public void setHouse(final String house) {
		this.house = house;
	}

	public void setNip(final String nip) {
		this.nip = nip;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	@Override
	public void setUser(final String user) {
		this.user = user;
	}

	public void setZip(final String zip) {
		this.zip = zip;
	}


}
