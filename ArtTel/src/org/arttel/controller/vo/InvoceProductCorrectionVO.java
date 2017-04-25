package org.arttel.controller.vo;


public class InvoceProductCorrectionVO extends FinancialDocumentProductVO {

	private String invoceProductCorrectionId;
	private String invoiceId;
	private String quantityDiff;
	private String netSumAmount;
	private String netSumAmountDiff;
	private String vatAmount;
	private String vatAmountDiff;
	private String grossSumAmount;
	private String grossSumAmountDiff;

	public String getGrossSumAmount() {
		return grossSumAmount;
	}
	public String getGrossSumAmountDiff() {
		return grossSumAmountDiff;
	}
	public String getInvoceProductCorrectionId() {
		return invoceProductCorrectionId;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public String getNetSumAmount() {
		return netSumAmount;
	}
	public String getNetSumAmountDiff() {
		return netSumAmountDiff;
	}
	public String getQuantityDiff() {
		return quantityDiff;
	}
	public String getVatAmount() {
		return vatAmount;
	}
	public String getVatAmountDiff() {
		return vatAmountDiff;
	}
	public void setGrossSumAmount(final String grossSumAmount) {
		this.grossSumAmount = grossSumAmount;
	}
	public void setGrossSumAmountDiff(final String grossSumAmountDiff) {
		this.grossSumAmountDiff = grossSumAmountDiff;
	}
	public void setInvoceProductCorrectionId(final String invoceProductCorrectionId) {
		this.invoceProductCorrectionId = invoceProductCorrectionId;
	}
	public void setInvoiceId(final String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public void setNetSumAmount(final String netSumAmount) {
		this.netSumAmount = netSumAmount;
	}
	public void setNetSumAmountDiff(final String netSumAmountDiff) {
		this.netSumAmountDiff = netSumAmountDiff;
	}
	public void setQuantityDiff(final String quantityDiff) {
		this.quantityDiff = quantityDiff;
	}

	public void setVatAmount(final String vatAmount) {
		this.vatAmount = vatAmount;
	}
	public void setVatAmountDiff(final String vatAmountDiff) {
		this.vatAmountDiff = vatAmountDiff;
	}
}
