package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public abstract class FinancialDocumentProductVO {

	private String invoiceProductId;
	private ProductVO productDefinition;
	private String quantity;

	public String getInvoiceProductId() {
		return invoiceProductId;
	}
	public ProductVO getProductDefinition() {
		return productDefinition;
	}
	public String getQuantity() {
		return quantity;
	}
	public void populate(final HttpServletRequest request, final String prefix) {
		quantity = Translator.parseIntegerStr(request.getParameter(prefix + "quantity"));
	}
	public void setInvoiceProductId(final String invoiceProductId) {
		this.invoiceProductId = invoiceProductId;
	}
	public void setProductDefinition(final ProductVO productDefinition) {
		this.productDefinition = productDefinition;
	}

	public void setQuantity(final String quantity) {
		this.quantity = quantity;
	}
}
