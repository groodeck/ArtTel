package org.arttel.controller.vo.filter;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public class AgreementFilterVO {
	
	private static final String COMPONENT_NAME = "agreementFilter";

	private String surname;
	private Date dateFrom;
	private Date dateTo;
	private String phrase;
	
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	public void populate( final HttpServletRequest request ) {
		phrase = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".phrase"));
		surname = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".surname"));
		dateFrom = Translator.parseDate(request.getParameter( COMPONENT_NAME + ".dateFrom" ), null);
		dateTo = Translator.parseDate( request.getParameter( COMPONENT_NAME + ".dateTo" ), null);
	}
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
}
