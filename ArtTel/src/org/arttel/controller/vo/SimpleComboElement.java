package org.arttel.controller.vo;

import org.arttel.view.ComboElement;

public class SimpleComboElement implements ComboElement {
	
	private String cityIdn;
	private String cityDesc;
	
	public SimpleComboElement(String cityIdn, String cityDesc) {
		super();
		this.cityIdn = cityIdn;
		this.cityDesc = cityDesc;
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
	
	@Override
	public String getIdn() {
		return getCityIdn();
	}

	@Override
	public String getDesc() {
		return getCityDesc();
	}
}