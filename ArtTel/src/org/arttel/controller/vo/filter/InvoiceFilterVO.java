package org.arttel.controller.vo.filter;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public final class InvoiceFilterVO {

	private static final String COMPONENT_NAME = "invoiceFilter";

	private String number;
	private Date createDate;
	private String user;
	
	public void populate( final HttpServletRequest request) {
		number = Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".number" ));
		createDate = Translator.parseDate(request.getParameter( COMPONENT_NAME + ".createDate" ));
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}