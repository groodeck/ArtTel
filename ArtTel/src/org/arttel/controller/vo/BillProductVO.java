package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;


public class BillProductVO extends FinancialDocumentProductVO {

	private String sumAmount;
	private String taxReleaseBasis;
	private String productClassification;

	public String getAmountDecimalPart(){
		return Translator.getNumberParts(sumAmount).getDecimalPart();
	}

	public String getAmountWholePart(){
		return Translator.getNumberParts(sumAmount).getWholePart();
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