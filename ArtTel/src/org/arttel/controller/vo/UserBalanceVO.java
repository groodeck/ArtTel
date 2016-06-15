package org.arttel.controller.vo;

import org.arttel.util.Translator;


public class UserBalanceVO extends BasePageVO {

	private String balanceId;
	private String userName;
	private String costSum;
	private String incomeSum;
	private String gain;
	private String amountLeft;
	private String dealingYear;

	public String getAmountLeft() {
		return amountLeft;
	}

	public String getBalanceId() {
		return balanceId;
	}

	public String getCostSum() {
		return costSum;
	}

	public String getDealingYear() {
		return dealingYear;
	}

	public String getGain() {
		return gain;
	}

	@Override
	public Integer getId() {
		return Translator.parseInteger(getBalanceId());
	}

	public String getIncomeSum() {
		return incomeSum;
	}

	@Override
	public String getUser() {
		return userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setAmountLeft(final String amountLeft) {
		this.amountLeft = amountLeft;
	}

	public void setBalanceId(final String balanceId) {
		this.balanceId = balanceId;
	}

	public void setCostSum(final String costSum) {
		this.costSum = costSum;
	}

	public void setDealingYear(final String dealingYear) {
		this.dealingYear = dealingYear;
	}

	@Override
	protected void setEditable(final boolean editable) {
	}

	public void setGain(final String gain) {
		this.gain = gain;
	}

	public void setIncomeSum(final String incomeSum) {
		this.incomeSum = incomeSum;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}
}
