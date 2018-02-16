package org.arttel.controller.vo;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.DealingFilterVO;
import org.arttel.dictionary.DealingType;
import org.arttel.dictionary.DocumentType;
import org.arttel.util.Translator;

public class DealingVO extends BasePageVO {

	private DealingFilterVO dealingFilter = new DealingFilterVO();

	private String dealingId;
	private DealingType dealingType;
	private Date date;
	private String city;
	private String corporateCosts;
	private String privateCosts;
	private String incomeClientId; // clientId
	private String incomeClientName; //clientName
	private String fuel;
	private String fuelLiters;
	private String machine;
	private String amount;
	private String comments1;
	private String comments2;
	private String comments3;
	private String userName;
	private boolean editable;
	private Integer documentId;
	private String documentNumber;
	private DocumentType documentType;

	public String getAmount() {
		return amount;
	}

	public String getCity() {
		return city;
	}

	public String getComments1() {
		return comments1;
	}

	public String getComments2() {
		return comments2;
	}

	public String getComments3() {
		return comments3;
	}

	public String getCorporateCosts() {
		return corporateCosts;
	}

	private String getCorporateCostsTitle() {
		return corporateCosts;
	}

	private String getCostTitle() {
		final String result;
		if(StringUtils.isNotEmpty(getCorporateCosts())){
			result = getCorporateCostsTitle();
		} else if (StringUtils.isNotEmpty(getPrivateCosts())){
			result = getPrivateCosts();
		} else if(StringUtils.isNotEmpty(getFuel())){
			result = getFuel();
		} else {
			result = null;
		}
		return result;
	}

	public Date getDate() {
		return date;
	}

	public DealingFilterVO getDealingFilter() {
		return dealingFilter;
	}

	public String getDealingId() {
		return dealingId;
	}

	public String getDealingTitle(){
		final String dealingTitle;
		if(getDealingType() == DealingType.COSTS){
			dealingTitle = getCostTitle();
		} else if (getDealingType() == DealingType.INCOME){
			dealingTitle = getIncomeClientName();
		} else {
			dealingTitle = "";
		}
		return dealingTitle;
	}

	public DealingType getDealingType() {
		return dealingType;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public String getFuel() {
		return fuel;
	}

	public String getFuelLiters() {
		return fuelLiters;
	}

	@Override
	public Integer getId() {
		return Translator.parseInteger(getDealingId());
	}

	public String getIncomeClientId() {
		return incomeClientId;
	}

	public String getIncomeClientName() {
		return incomeClientName;
	}

	public String getMachine() {
		return machine;
	}

	public String getPrivateCosts() {
		return privateCosts;
	}

	@Override
	public String getUser() {
		return userName;
	}

	public String getUserId() {
		return userName;
	}

	public boolean isEditable() {
		return editable;
	}

	public void populate( final HttpServletRequest request ) {

		dealingId = request.getParameter("dealingId");
		final String dealingTypeStr = request.getParameter("dealingType");
		if(dealingTypeStr != null){
			dealingType = DealingType.getValueByIdn(dealingTypeStr);
		}
		final String dateStr = request.getParameter("date");
		date = Translator.parseDate(dateStr, null);
		city = request.getParameter("city");
		if(dealingType == DealingType.COSTS){
			corporateCosts = request.getParameter("corporateCosts");
			privateCosts = request.getParameter("privateCosts");
			fuel = request.getParameter("fuel");
			final String fuelLitersRaw = request.getParameter("fuelLiters");
			final String fuelLitersEmptyHandled = Translator.emptyAsNull(fuelLitersRaw);
			fuelLiters = Translator.parseDecimalStr(fuelLitersEmptyHandled);
			machine = request.getParameter("machine");
		} else if(dealingType == DealingType.INCOME){
			incomeClientId = request.getParameter("income");
		}
		final String amountRaw = request.getParameter("amount");
		final String amountEmptyHandled = Translator.emptyAsNull(amountRaw);
		amount = Translator.parseDecimalStr(amountEmptyHandled);
		comments1 = request.getParameter("comments1");
		comments2 = request.getParameter("comments2");
		comments3 = request.getParameter("comments3");
		userName = request.getParameter("userName");
		documentNumber = request.getParameter("documentNumber");
		final String documentTypeStr = request.getParameter("documentType");
		if(documentTypeStr != null) {
			documentType = DocumentType.getValueByIdn(documentTypeStr);
		}
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setComments1(final String comments1) {
		this.comments1 = comments1;
	}

	public void setComments2(final String comments2) {
		this.comments2 = comments2;
	}

	public void setComments3(final String comments3) {
		this.comments3 = comments3;
	}

	public void setCorporateCosts(final String corporateCosts) {
		this.corporateCosts = corporateCosts;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public void setDealingFilter(final DealingFilterVO dealingFilter) {
		this.dealingFilter = dealingFilter;
	}

	public void setDealingId(final String dealingId) {
		this.dealingId = dealingId;
	}

	public void setDealingType(final DealingType dealingType) {
		this.dealingType = dealingType;
	}

	public void setDocumentId(final Integer documentId) {
		this.documentId = documentId;
	}

	public void setDocumentNumber(final String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setDocumentType(final DocumentType documentType) {
		this.documentType = documentType;
	}

	@Override
	protected void setEditable(final boolean editable) {
		this.editable = editable;
	}

	public void setFuel(final String fuel) {
		this.fuel = fuel;
	}

	public void setFuelLiters(final String fuelLiters) {
		this.fuelLiters = fuelLiters;
	}

	public void setIncomeClientId(final String clientId) {
		incomeClientId = clientId;
	}

	public void setIncomeClientName(final String clientName) {
		incomeClientName = clientName;
	}

	public void setMachine(final String machine) {
		this.machine = machine;
	}

	public void setPrivateCosts(final String privateCosts) {
		this.privateCosts = privateCosts;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

}
