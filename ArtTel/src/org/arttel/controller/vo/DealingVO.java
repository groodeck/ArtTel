package org.arttel.controller.vo;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.DealingFilterVO;
import org.arttel.dictionary.DealingType;
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

	private String getCorporateCostsTitle() {
		return corporateCosts;
	}
	
	public String getUser() {
		return userName;
	}

	@Override
	protected void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getDealingId() {
		return dealingId;
	}

	public void setDealingId(String dealingId) {
		this.dealingId = dealingId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCorporateCosts() {
		return corporateCosts;
	}

	public void setCorporateCosts(String corporateCosts) {
		this.corporateCosts = corporateCosts;
	}

	public String getPrivateCosts() {
		return privateCosts;
	}

	public void setPrivateCosts(String privateCosts) {
		this.privateCosts = privateCosts;
	}

	public String getIncomeClientId() {
		return incomeClientId;
	}

	public void setIncomeClientId(String clientId) {
		this.incomeClientId = clientId;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getComments1() {
		return comments1;
	}

	public void setComments1(String comments1) {
		this.comments1 = comments1;
	}

	public String getComments2() {
		return comments2;
	}

	public void setComments2(String comments2) {
		this.comments2 = comments2;
	}

	public String getComments3() {
		return comments3;
	}

	public void setComments3(String comments3) {
		this.comments3 = comments3;
	}

	public String getUserId() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public DealingFilterVO getDealingFilter() {
		return dealingFilter;
	}

	public void setDealingFilter(DealingFilterVO dealingFilter) {
		this.dealingFilter = dealingFilter;
	}

	public boolean isEditable() {
		return editable;
	}

	public DealingType getDealingType() {
		return dealingType;
	}

	public void setDealingType(DealingType dealingType) {
		this.dealingType = dealingType;
	}

	public String getFuelLiters() {
		return fuelLiters;
	}

	public void setFuelLiters(String fuelLiters) {
		this.fuelLiters = fuelLiters;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIncomeClientName() {
		return incomeClientName;
	}

	public void setIncomeClientName(final String clientName) {
		this.incomeClientName = clientName;
	}

}
