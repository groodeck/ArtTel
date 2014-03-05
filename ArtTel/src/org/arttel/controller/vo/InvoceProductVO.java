package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public class InvoceProductVO {
	
	private String invoiceProductId;
	private ProductVO productDefinition;
	private String quantity;
	private String netSumAmount;
	private String vatAmount;
	private String grossSumAmount;
	
	private InvoceProductCorrectionVO correction;
	
	public void populate(HttpServletRequest request, String prefix) {
		quantity = Translator.parseIntegerStr(request.getParameter(prefix + "quantity"));
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

	public InvoceProductCorrectionVO getCorrection() {
		return correction;
	}

	public void setCorrection(InvoceProductCorrectionVO correction) {
		this.correction = correction;
	}

	public String getInvoiceProductId() {
		return invoiceProductId;
	}

	public void setInvoiceProductId(String invoiceProductId) {
		this.invoiceProductId = invoiceProductId;
	}

}
