package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public class CityVO  extends FormsDictionaryVO {

	private String cityId;
	private String cityIdn;
	private String cityDesc;

	public CityVO(){
	}

	public CityVO(final String id, final String idn, final String desc){
		cityId = id;
		cityIdn = idn;
		cityDesc = desc;
	}

	public String getCityDesc() {
		return cityDesc;
	}
	public String getCityId() {
		return cityId;
	}
	public String getCityIdn() {
		return cityIdn;
	}
	@Override
	public Integer getId() {
		return Translator.parseInteger(getCityId());
	}
	@Override
	protected String getUser() {
		// not used yet
		return null;
	}
	public void populate(final HttpServletRequest request, final String prefix) {
		setCityDesc(request.getParameter(prefix + "cityDesc"));
		setForInstalation(Translator.getBoolean(request.getParameter(prefix + "forInstalation")));
		setForOrder(Translator.getBoolean(request.getParameter(prefix + "forOrder")));
		setForReport(Translator.getBoolean(request.getParameter(prefix + "forReport")));
		setForSqueeze(Translator.getBoolean(request.getParameter(prefix + "forSqueeze")));
		setForDealing(Translator.getBoolean(request.getParameter(prefix + "forDealing")));
	}

	public void setCityDesc(final String cityDesc) {
		this.cityDesc = cityDesc;
	}

	public void setCityId(final String cityId) {
		this.cityId = cityId;
	}

	public void setCityIdn(final String cityIdn) {
		this.cityIdn = cityIdn;
	}

	@Override
	protected void setEditable(final boolean editable) {
		// do nothing
	}
}
