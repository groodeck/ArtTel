package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;
import org.arttel.view.ComboElement;

public class CompanyCostVO extends FormsDictionaryVO implements ComboElement {

	private String companyCostId;
	private String companyCostIdn;
	private String companyCostDesc;

	public CompanyCostVO(){
	}

	public CompanyCostVO(final String companyCostId, final String companyCostIdn, final String companyCostDesc) {
		this.companyCostId = companyCostId;
		this.companyCostIdn = companyCostIdn;
		this.companyCostDesc = companyCostDesc;
	}

	public String getCompanyCostDesc() {
		return companyCostDesc;
	}
	public String getCompanyCostId() {
		return companyCostId;
	}

	public String getCompanyCostIdn() {
		return companyCostIdn;
	}

	@Override
	public String getDesc() {
		return companyCostDesc;
	}

	@Override
	public Integer getId() {
		return Translator.parseInteger(getCompanyCostId());
	}

	@Override
	public String getIdn() {
		return companyCostIdn;
	}

	@Override
	protected String getUser() {
		// not used yet
		return null;
	}

	public void populate(final HttpServletRequest request, final String prefix) {
		companyCostDesc = request.getParameter(prefix + "companyCostDesc");
		setForInstalation(Translator.getBoolean(request.getParameter(prefix + "forInstalation")));
		setForOrder(Translator.getBoolean(request.getParameter(prefix + "forOrder")));
		setForReport(Translator.getBoolean(request.getParameter(prefix + "forReport")));
		setForSqueeze(Translator.getBoolean(request.getParameter(prefix + "forSqueeze")));
		setForDealing(Translator.getBoolean(request.getParameter(prefix + "forDealing")));
	}

	public void setCompanyCostDesc(final String companyCostDesc) {
		this.companyCostDesc = companyCostDesc;
	}

	public void setCompanyCostId(final String companyCostId) {
		this.companyCostId = companyCostId;
	}

	public void setCompanyCostIdn(final String companyCostIdn) {
		this.companyCostIdn = companyCostIdn;
	}

	@Override
	protected void setEditable(final boolean editable) {
		// do nothing
	}
}
