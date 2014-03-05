package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;
import org.arttel.view.ComboElement;

public class CompanyCostVO extends FormsDictionaryVO implements ComboElement {

	private String companyCostId;
	private String companyCostIdn;
	private String companyCostDesc;
	
	public CompanyCostVO(String companyCostId, String companyCostIdn, String companyCostDesc) {
		this.companyCostId = companyCostId;
		this.companyCostIdn = companyCostIdn;
		this.companyCostDesc = companyCostDesc;
	}
	
	public CompanyCostVO(){
	}

	@Override
	public String getIdn() {
		return companyCostIdn;
	}
	@Override
	public String getDesc() {
		return companyCostDesc;
	}

	public String getCompanyCostId() {
		return companyCostId;
	}

	public void setCompanyCostId(String companyCostId) {
		this.companyCostId = companyCostId;
	}

	public String getCompanyCostIdn() {
		return companyCostIdn;
	}

	public void setCompanyCostIdn(String companyCostIdn) {
		this.companyCostIdn = companyCostIdn;
	}

	public String getCompanyCostDesc() {
		return companyCostDesc;
	}

	public void setCompanyCostDesc(String companyCostDesc) {
		this.companyCostDesc = companyCostDesc;
	}
	
	public void populate(HttpServletRequest request, String prefix) {
		companyCostDesc = request.getParameter(prefix + "companyCostDesc");
		setForInstalation(Translator.getBoolean(request.getParameter(prefix + "forInstalation")));
		setForOrder(Translator.getBoolean(request.getParameter(prefix + "forOrder")));
		setForReport(Translator.getBoolean(request.getParameter(prefix + "forReport")));
		setForSqueeze(Translator.getBoolean(request.getParameter(prefix + "forSqueeze")));
		setForDealing(Translator.getBoolean(request.getParameter(prefix + "forDealing")));
	}
}
