package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public class InvoceProductCorrectionVO {

	private String invoceProductCorrectionId;
	private String invoceProductId;
	private String invoiceId;
	private ProductVO productDefinition;
	private String quantity;
	private String quantityDiff;
	private String netSumAmount;
	private String netSumAmountDiff;
	private String vatAmount;
	private String vatAmountDiff;
	private String grossSumAmount;
	private String grossSumAmountDiff;
	
	public String getInvoceProductCorrectionId() {
		return invoceProductCorrectionId;
	}
	public void setInvoceProductCorrectionId(String invoceProductCorrectionId) {
		this.invoceProductCorrectionId = invoceProductCorrectionId;
	}
	public String getInvoceProductId() {
		return invoceProductId;
	}
	public void setInvoceProductId(String invoceProductId) {
		this.invoceProductId = invoceProductId;
	}
	public ProductVO getProductDefinition() {
		return productDefinition;
	}
	public void setProductDefinition(ProductVO productDefinition) {
		this.productDefinition = productDefinition;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getNetSumAmount() {
		return netSumAmount;
	}
	public void setNetSumAmount(String netSumAmount) {
		this.netSumAmount = netSumAmount;
	}
	public String getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(String vatAmount) {
		this.vatAmount = vatAmount;
	}
	public String getGrossSumAmount() {
		return grossSumAmount;
	}
	public void setGrossSumAmount(String grossSumAmount) {
		this.grossSumAmount = grossSumAmount;
	}
	public String getQuantityDiff() {
		return quantityDiff;
	}
	public void setQuantityDiff(String quantityDiff) {
		this.quantityDiff = quantityDiff;
	}
	public String getNetSumAmountDiff() {
		return netSumAmountDiff;
	}
	public void setNetSumAmountDiff(String netSumAmountDiff) {
		this.netSumAmountDiff = netSumAmountDiff;
	}
	public String getVatAmountDiff() {
		return vatAmountDiff;
	}
	public void setVatAmountDiff(String vatAmountDiff) {
		this.vatAmountDiff = vatAmountDiff;
	}
	public String getGrossSumAmountDiff() {
		return grossSumAmountDiff;
	}
	public void setGrossSumAmountDiff(String grossSumAmountDiff) {
		this.grossSumAmountDiff = grossSumAmountDiff;
	}
	
	public void populate(final HttpServletRequest request, final String prefix) {
		quantity = Translator.parseIntegerStr(request.getParameter(prefix + "quantity"));
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
}
