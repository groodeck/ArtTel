package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public class CityVO  extends FormsDictionaryVO {

	private String cityId;
	private String cityIdn;
	private String cityDesc;
	
	public CityVO(String id, String idn, String desc){
		this.cityId = id;
		this.cityIdn = idn;
		this.cityDesc = desc;
	}
	
	public CityVO(){
	}
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityIdn() {
		return cityIdn;
	}
	public void setCityIdn(String cityIdn) {
		this.cityIdn = cityIdn;
	}
	public String getCityDesc() {
		return cityDesc;
	}
	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}

	public void populate(HttpServletRequest request, String prefix) {
		setCityDesc(request.getParameter(prefix + "cityDesc"));
		setForInstalation(Translator.getBoolean(request.getParameter(prefix + "forInstalation")));
		setForOrder(Translator.getBoolean(request.getParameter(prefix + "forOrder")));
		setForReport(Translator.getBoolean(request.getParameter(prefix + "forReport")));
		setForSqueeze(Translator.getBoolean(request.getParameter(prefix + "forSqueeze")));
		setForDealing(Translator.getBoolean(request.getParameter(prefix + "forDealing")));
	}
}
