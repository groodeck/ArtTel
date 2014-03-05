package org.arttel.controller.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.arttel.business.IncomeBalanceCalculator;

public class SettingsVO extends BasePageVO {
	
	private String selectedUserId;
	private List<UserBalanceVO> userBalanceList;
	private FundsEntryVO newFunds = new FundsEntryVO();
	private List<FundsEntryVO> fundsEntryList;
	private CityVO newCity = new CityVO();
	private List<CityVO> cityList;
	private CompanyCostVO newCompanyCost = new CompanyCostVO();
	private List<CompanyCostVO >companyCostsList;
	
	public void populate( final HttpServletRequest request ) {
		selectedUserId = request.getParameter("user");
		newFunds.populate(request, "newFunds.");
	}
	
	@Override
	protected void setEditable(boolean editable) {
	}

	@Override
	protected String getUser() {
		return null;
	}

	public List getUserBalanceList() {
		return userBalanceList;
	}

	public void setUserBalanceList(List userBalanceList) {
		this.userBalanceList = userBalanceList;
	}

	public FundsEntryVO getNewFunds() {
		return newFunds;
	}

	public void setNewFunds(FundsEntryVO newFunds) {
		this.newFunds = newFunds;
	}

	public List<FundsEntryVO> getFundsEntryList() {
		return fundsEntryList;
	}

	public void setFundsEntryList(List<FundsEntryVO> fundsEntryList) {
		this.fundsEntryList = fundsEntryList;
	}

	public String getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserId(String selectedUserId) {
		this.selectedUserId = selectedUserId;
	}
	
	public String getFundsEntrySum(){
		final IncomeBalanceCalculator balanceCalculator = new IncomeBalanceCalculator();
		final BigDecimal fundsEntrySum = balanceCalculator.calculateFundsEntrySum(fundsEntryList);
		return fundsEntrySum.toPlainString();
	}

	public void setCityList(List<CityVO> cityList) {
		this.cityList = cityList; 
	}

	public List<CityVO> getCityList() {
		return cityList;
	}

	public CityVO getNewCity() {
		return newCity;
	}

	public void setNewCity(CityVO newCity) {
		this.newCity = newCity;
	}

	public void populateNewCity(HttpServletRequest request) {
		newCity.populate(request, "newCity.");
	}
	
	public void populateNewCompanyCost(HttpServletRequest request) {
		newCompanyCost.populate(request, "newCompanyCost.");
	}

	public CompanyCostVO getNewCompanyCost() {
		return newCompanyCost;
	}

	public void setNewCompanyCost(CompanyCostVO newCompanyCost) {
		this.newCompanyCost = newCompanyCost;
	}

	public List<CompanyCostVO> getCompanyCostsList() {
		return companyCostsList;
	}

	public void setCompanyCostsList(List<CompanyCostVO> companyCostsList) {
		this.companyCostsList = companyCostsList;
	}
	
}