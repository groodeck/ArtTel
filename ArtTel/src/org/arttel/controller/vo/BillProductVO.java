package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;


public class BillProductVO extends FinancialDocumentProductVO {

	private String sumAmount;
	private String taxReleaseBasis;
	private String productClassification;

	public String getAmountDecimalPart(){
		return ""; //TODO
	}

	public String getAmountWholePart(){
		return "";//TODO
	}

	public String getProductClassification() {
		return productClassification;
	}

	public String getSumAmount() {
		return sumAmount;
	}

	public String getTaxReleaseBasis() {
		return taxReleaseBasis;
	}

	@Override
	public void populate(final HttpServletRequest request, final String prefix) {
		super.populate(request, prefix);
		taxReleaseBasis = request.getParameter(prefix + "taxReleaseBasis");
		productClassification = request.getParameter(prefix + "productClassification");
	}

	public void setProductClassification(final String productClassification) {
		this.productClassification = productClassification;
	}

	public void setSumAmount(final String amount) {
		sumAmount = amount;
	}

	public void setTaxReleaseBasis(final String taxReleaseBasis) {
		this.taxReleaseBasis = taxReleaseBasis;
	}
}