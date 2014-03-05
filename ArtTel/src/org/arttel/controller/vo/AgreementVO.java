package org.arttel.controller.vo;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.AgreementFilterVO;
import org.arttel.dictionary.Status;
import org.arttel.util.Translator;

public class AgreementVO extends BasePageVO {
	
	private AgreementFilterVO agreementFilter = new AgreementFilterVO();
	
	private String agreementId;
	private Status status;
	private Date signDate;
	private String client;
	private String agreementNumber;
	private String name;
	private String surname;
	private String street;
	private String house;
	private String apartment;
	private String phone;
	private String bundle;
	private String city;
	private String signPlace;
	private String agentDeal;
	private Date installationDate;
	private Date evidenceEntryDate;
	private String salesmanCode;
	private String subscriberNumber;
	private Date agreementEndDate;
	private String comments;
	private String additionalComments;
	private String user;
	private boolean editable;

	public void populate( final HttpServletRequest request ) {
		
		agreementId = request.getParameter("agreementId");
		final String statusStr = request.getParameter("status");
		if(statusStr != null){
			status = Status.getValueByIdn(statusStr);
		}
		final String signDateStr = request.getParameter("signDate"); 
		signDate = Translator.parseDate(signDateStr, null);
		client = request.getParameter("client");
		agreementNumber = request.getParameter("agreementNumber");
		name = request.getParameter("name");
		surname = request.getParameter("surname");
		street = request.getParameter("street");
		house = request.getParameter("house");
		apartment = request.getParameter("apartment");
		phone = request.getParameter("phone");
		bundle = request.getParameter("bundle");
		city = request.getParameter("city");
		signPlace = request.getParameter("signPlace");
		agentDeal = request.getParameter("agentDeal");
		final String installationDateStr = request.getParameter("installationDate"); 
		installationDate = Translator.parseDate(installationDateStr, null);
		final String evidenceEntryDateStr = request.getParameter("evidenceEntryDate"); 
		evidenceEntryDate = Translator.parseDate(evidenceEntryDateStr, null);
		salesmanCode = request.getParameter("salesmanCode");
		subscriberNumber = request.getParameter("subscriberNumber");
		final String agreementEndDateStr = request.getParameter("agreementEndDate"); 
		agreementEndDate = Translator.parseDate(agreementEndDateStr, null);
		comments = request.getParameter("comments");
		additionalComments = request.getParameter("additionalComments");
		user = request.getParameter("user");
	}

	public AgreementFilterVO getAgreementFilter() {
		return agreementFilter;
	}

	public void setAgreementFilter(AgreementFilterVO agreementFilter) {
		this.agreementFilter = agreementFilter;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	
	public String getAddress() {
		final StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotEmpty(street)){
			sb.append(street);
			sb.append(house != null ? " " + house : "");
			sb.append(apartment != null ? "/" + apartment : "");
		}
		return sb.toString();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getBundle() {
		return bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
}
