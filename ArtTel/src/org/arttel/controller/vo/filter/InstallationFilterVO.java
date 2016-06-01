package org.arttel.controller.vo.filter;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public class InstallationFilterVO {
	

	private static final String COMPONENT_NAME = "instalationFilter";

	private String city;
	private String status;
	private Date dateFrom;
	private Date dateTo;
	private String phrase;
	private String instalationType;
	private String user;
	private String serial;
	private String mac;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
		city = Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".city" ));
		status =  Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".status" ));
		dateFrom = Translator.parseDate(request.getParameter( COMPONENT_NAME + ".dateFrom" ), null);
		dateTo = Translator.parseDate( request.getParameter( COMPONENT_NAME + ".dateTo" ), null);
		phrase = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".phrase"));
		instalationType = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".instalationType"));
		user = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".user"));
		serial = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".serial"));
		mac = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".mac"));
	}
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public String getInstalationType() {
		return instalationType;
	}
	public void setInstalationType(String instalationType) {
		this.instalationType = instalationType;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
}
