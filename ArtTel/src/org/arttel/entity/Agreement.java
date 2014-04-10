package org.arttel.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Agreement")
public class Agreement {

	@Id
	@GeneratedValue
	@Column(name="AgreementId")
	private Integer agreementId; 
	
	@Column(name="Status")
	private String status; 
	
	@Column(name="SignDate")
	private Date signDate; 
	
	@Column(name="Name")
	private String name; 
	
	@Column(name="Surname")
	private String surname; 
	
	@Column(name="Street")
	private String street; 
	
	@Column(name="City")
	private String city; 
	
	@Column(name="AgreementNumber")
	private String agreementNumber; 
	
	@Column(name="Client")
	private String client; 
	
	@Column(name="House")
	private String house; 
	
	@Column(name="Apartment")
	private String apartment; 
	
	@Column(name="Phone")
	private String phone; 
	
	@Column(name="SignPlace")
	private String signPlace; 
	
	@Column(name="AgentDeal")
	private String agentDeal; 
	
	@Column(name="InstallationDate")
	private Date installationDate; 
	
	@Column(name="EvidenceEntryDate")
	private Date evidenceEntryDate; 
	
	@Column(name="SalesmanCode")
	private String salesmanCode;
	
	@Column(name="SubscriberNumber")
	private String subscriberNumber;
	
	@Column(name="AgreementEndDate")
	private Date agreementEndDate; 
	
	@Column(name="Comments")
	private String comments; 
	
	@Column(name="AdditionalComments")
	private String additionalComments; 
	
	@Column(name="User")
	private String user; 
	
	@Column(name="Bundle")
	private String bundle;

	public Integer getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Integer agreementId) {
		this.agreementId = agreementId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSignPlace() {
		return signPlace;
	}

	public void setSignPlace(String signPlace) {
		this.signPlace = signPlace;
	}

	public String getAgentDeal() {
		return agentDeal;
	}

	public void setAgentDeal(String agentDeal) {
		this.agentDeal = agentDeal;
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public Date getEvidenceEntryDate() {
		return evidenceEntryDate;
	}

	public void setEvidenceEntryDate(Date evidenceEntryDate) {
		this.evidenceEntryDate = evidenceEntryDate;
	}

	public String getSalesmanCode() {
		return salesmanCode;
	}

	public void setSalesmanCode(String salesmanCode) {
		this.salesmanCode = salesmanCode;
	}

	public String getSubscriberNumber() {
		return subscriberNumber;
	}

	public void setSubscriberNumber(String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
	}

	public Date getAgreementEndDate() {
		return agreementEndDate;
	}

	public void setAgreementEndDate(Date agreementEndDate) {
		this.agreementEndDate = agreementEndDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAdditionalComments() {
		return additionalComments;
	}

	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getBundle() {
		return bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	
	
}
