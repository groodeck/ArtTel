package org.arttel.controller.vo;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class FundsEntryVO {
	
	private String fundsEntryId;
	private String entryAmount;
	private Date entryDate;
	private String userName;
	private String comments;
	
	public void populate( final HttpServletRequest request, final String paramPrefix ) {
		userName = request.getParameter(paramPrefix + "user");
		entryDate = new java.sql.Date(new Date().getTime());
		entryAmount = request.getParameter(paramPrefix + "amount");
		comments = request.getParameter(paramPrefix + "comments");
	}
	
	public String getFundsEntryId() {
		return fundsEntryId;
	}
	public void setFundsEntryId(String fundsEntryId) {
		this.fundsEntryId = fundsEntryId;
	}
	public String getEntryAmount() {
		return entryAmount;
	}
	public void setEntryAmount(String entryAmount) {
		this.entryAmount = entryAmount;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
