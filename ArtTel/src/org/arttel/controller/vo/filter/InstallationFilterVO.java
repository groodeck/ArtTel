package org.arttel.controller.vo.filter;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;
import org.assertj.core.util.Lists;

import com.google.common.collect.Iterables;

public class InstallationFilterVO {


	private static final String COMPONENT_NAME = "instalationFilter";

	private String city;
	private String status;
	private Date dateFrom;
	private Date dateTo;
	private String phrase;
	private List<String> installationTypes;
	private String user;
	private String serial;
	private String mac;

	public InstallationFilterVO(){
		installationTypes = Lists.newArrayList();
	}

	public String getCity() {
		return city;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public String getFirstInstalationType(){
		if(installationTypes.isEmpty()){
			return null;
		} else {
			return Iterables.getFirst(installationTypes, null);
		}
	}
	public List<String> getInstalationType() {
		return installationTypes;
	}
	public String getMac() {
		return mac;
	}
	public String getPhrase() {
		return phrase;
	}
	public String getSerial() {
		return serial;
	}

	public String getStatus() {
		return status;
	}
	public String getUser() {
		return user;
	}
	public void populate( final HttpServletRequest request ) {
		city = Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".city" ));
		status =  Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".status" ));
		dateFrom = Translator.parseDate(request.getParameter( COMPONENT_NAME + ".dateFrom" ), null);
		dateTo = Translator.parseDate( request.getParameter( COMPONENT_NAME + ".dateTo" ), null);
		phrase = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".phrase"));
		final String installationTypeStr = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".instalationType"));
		installationTypes = Lists.newArrayList(installationTypeStr);
		if(installationTypeStr != null){
			installationTypes.add(installationTypeStr);
		}
		user = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".user"));
		serial = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".serial"));
		mac = Translator.emptyAsNull(request.getParameter(COMPONENT_NAME + ".mac"));
	}
	public void setCity(final String city) {
		this.city = city;
	}
	public void setDateFrom(final Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public void setDateTo(final Date dateTo) {
		this.dateTo = dateTo;
	}
	public void setInstalationType(final List<String> instalationType) {
		installationTypes = instalationType;
	}
	public void setMac(final String mac) {
		this.mac = mac;
	}
	public void setPhrase(final String phrase) {
		this.phrase = phrase;
	}
	public void setSerial(final String serial) {
		this.serial = serial;
	}
	public void setStatus(final String status) {
		this.status = status;
	}

	public void setUser(final String user) {
		this.user = user;
	}
}
