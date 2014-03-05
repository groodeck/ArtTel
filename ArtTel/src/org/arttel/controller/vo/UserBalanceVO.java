package org.arttel.controller.vo;


public class UserBalanceVO extends BasePageVO {
	
	private String balanceId;
	private String userName;
	private String costSum;
	private String incomeSum;
	private String gain;
	private String amountLeft;
	private String dealingYear;

	@Override
	protected void setEditable(boolean editable) {
	}

	@Override
	public String getUser() {
		return userName;
	}

	public String getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}

	public String getCostSum() {
		return costSum;
	}

	public void setCostSum(String costSum) {
		this.costSum = costSum;
	}

	public String getIncomeSum() {
		return incomeSum;
	}

	public void setIncomeSum(String incomeSum) {
		this.incomeSum = incomeSum;
	}

	public String getGain() {
		return gain;
	}

	public void setGain(String gain) {
		this.gain = gain;
	}

	public String getAmountLeft() {
		return amountLeft;
	}

	public void setAmountLeft(String amountLeft) {
		this.amountLeft = amountLeft;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDealingYear() {
		return dealingYear;
	}
	
	public void setDealingYear(String dealingYear) {
		this.dealingYear = dealingYear;
	}
}
