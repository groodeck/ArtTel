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

	public String getAdditionalComments() {
		return additionalComments;
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

	public String getAgentDeal() {
		return agentDeal;
	}

	public Date getAgreementEndDate() {
		return agreementEndDate;
	}

	public AgreementFilterVO getAgreementFilter() {
		return agreementFilter;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public String getApartment() {
		return apartment;
	}

	public String getBundle() {
		return bundle;
	}

	public String getCity() {
		return city;
	}

	public String getClient() {
		return client;
	}

	public String getComments() {
		return comments;
	}

	public Date getEvidenceEntryDate() {
		return evidenceEntryDate;
	}

	public String getHouse() {
		return house;
	}

	@Override
	public Integer getId() {
		return Translator.parseInteger(getAgreementId());
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getSalesmanCode() {
		return salesmanCode;
	}

	public Date getSignDate() {
		return signDate;
	}

	public String getSignPlace() {
		return signPlace;
	}

	public Status getStatus() {
		return status;
	}

	public String getStreet() {
		return street;
	}

	public String getSubscriberNumber() {
		return subscriberNumber;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public String getUser() {
		return user;
	}

	public boolean isEditable() {
		return editable;
	}

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

	public void setAdditionalComments(final String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public void setAgentDeal(final String agentDeal) {
		this.agentDeal = agentDeal;
	}

	public void setAgreementEndDate(final Date agreementEndDate) {
		this.agreementEndDate = agreementEndDate;
	}

	public void setAgreementFilter(final AgreementFilterVO agreementFilter) {
		this.agreementFilter = agreementFilter;
	}

	public void setAgreementId(final String agreementId) {
		this.agreementId = agreementId;
	}

	public void setAgreementNumber(final String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public void setApartment(final String apartment) {
		this.apartment = apartment;
	}

	public void setBundle(final String bundle) {
		this.bundle = bundle;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setClient(final String client) {
		this.client = client;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	@Override
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	public void setEvidenceEntryDate(final Date evidenceEntryDate) {
		this.evidenceEntryDate = evidenceEntryDate;
	}

	public void setHouse(final String house) {
		this.house = house;
	}

	public void setInstallationDate(final Date installationDate) {
		this.installationDate = installationDate;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setSalesmanCode(final String salesmanCode) {
		this.salesmanCode = salesmanCode;
	}

	public void setSignDate(final Date signDate) {
		this.signDate = signDate;
	}

	public void setSignPlace(final String signPlace) {
		this.signPlace = signPlace;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public void setSubscriberNumber(final String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setUser(final String user) {
		this.user = user;
	}
}
