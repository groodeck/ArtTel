package org.arttel.controller.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.arttel.business.IncomeBalanceCalculator;

public class SettingsVO{

	private String selectedUserId;
	private List<UserBalanceVO> userBalanceList;
	private FundsEntryVO newFunds = new FundsEntryVO();
	private List<FundsEntryVO> fundsEntryList;
	private CityVO newCity = new CityVO();
	private List<CityVO> cityList;
	private CompanyCostVO newCompanyCost = new CompanyCostVO();
	private List<CompanyCostVO >companyCostsList;

	public List<CityVO> getCityList() {
		return cityList;
	}

	public List<CompanyCostVO> getCompanyCostsList() {
		return companyCostsList;
	}

	public List<FundsEntryVO> getFundsEntryList() {
		return fundsEntryList;
	}

	public String getFundsEntrySum(){
		final IncomeBalanceCalculator balanceCalculator = new IncomeBalanceCalculator();
		final BigDecimal fundsEntrySum = balanceCalculator.calculateFundsEntrySum(fundsEntryList);
		return fundsEntrySum.toPlainString();
	}

	public CityVO getNewCity() {
		return newCity;
	}

	public CompanyCostVO getNewCompanyCost() {
		return newCompanyCost;
	}

	public FundsEntryVO getNewFunds() {
		return newFunds;
	}

	public String getSelectedUserId() {
		return selectedUserId;
	}

	public List getUserBalanceList() {
		return userBalanceList;
	}

	public void populate( final HttpServletRequest request ) {
		selectedUserId = request.getParameter("user");
		newFunds.populate(request, "newFunds.");
	}

	public void populateNewCity(final HttpServletRequest request) {
		newCity.populate(request, "newCity.");
	}

	public void populateNewCompanyCost(final HttpServletRequest request) {
		newCompanyCost.populate(request, "newCompanyCost.");
	}

	public void setCityList(final List<CityVO> cityList) {
		this.cityList = cityList;
	}

	public void setCompanyCostsList(final List<CompanyCostVO> companyCostsList) {
		this.companyCostsList = companyCostsList;
	}

	public void setFundsEntryList(final List<FundsEntryVO> fundsEntryList) {
		this.fundsEntryList = fundsEntryList;
	}

	public void setNewCity(final CityVO newCity) {
		this.newCity = newCity;
	}

	public void setNewCompanyCost(final CompanyCostVO newCompanyCost) {
		this.newCompanyCost = newCompanyCost;
	}

	public void setNewFunds(final FundsEntryVO newFunds) {
		this.newFunds = newFunds;
	}

	public void setSelectedUserId(final String selectedUserId) {
		this.selectedUserId = selectedUserId;
	}

	public void setUserBalanceList(final List userBalanceList) {
		this.userBalanceList = userBalanceList;
	}

}