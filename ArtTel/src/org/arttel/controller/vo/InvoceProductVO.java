package org.arttel.controller.vo;


public class InvoceProductVO extends FinancialDocumentProductVO {

	private String netSumAmount;
	private String vatAmount;
	private String grossSumAmount;

	private InvoceProductCorrectionVO correction;

	public InvoceProductCorrectionVO getCorrection() {
		return correction;
	}

	public String getGrossSumAmount() {
		return grossSumAmount;
	}
	public String getNetSumAmount() {
		return netSumAmount;
	}
	public String getVatAmount() {
		return vatAmount;
	}
	public void setCorrection(final InvoceProductCorrectionVO correction) {
		this.correction = correction;
	}
	public void setGrossSumAmount(final String grossSumAmount) {
		this.grossSumAmount = grossSumAmount;
	}

	public void setNetSumAmount(final String netSumAmount) {
		this.netSumAmount = netSumAmount;
	}

	public void setVatAmount(final String vatAmount) {
		this.vatAmount = vatAmount;
	}
}
