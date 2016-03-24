package org.arttel.controller.vo.filter;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public final class InvoiceFilterVO {

	private String number;
	private Date createDate;
	private String user;

	public Date getCreateDate() {
		return createDate;
	}

	public String getNumber() {
		return number;
	}

	public String getUser() {
		return user;
	}

	public void populate( final String filterComponentName, final HttpServletRequest request) {
		number = Translator.emptyAsNull(request.getParameter(filterComponentName + ".number" ));
		createDate = Translator.parseDate(request.getParameter(filterComponentName + ".createDate" ));
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setUser(final String user) {
		this.user = user;
	}
}