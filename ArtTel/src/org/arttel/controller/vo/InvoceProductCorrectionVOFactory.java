package org.arttel.controller.vo;

public class InvoceProductCorrectionVOFactory {

	public InvoceProductCorrectionVO correctInvoiceProduct(final InvoceProductVO invoiceProduct){
		final InvoceProductCorrectionVO correction = new InvoceProductCorrectionVO();
		correction.setProductDefinition(invoiceProduct.getProductDefinition());
		correction.setInvoiceProductId(invoiceProduct.getInvoiceProductId());
		correction.setQuantity(invoiceProduct.getQuantity());
		correction.setNetSumAmount(invoiceProduct.getNetSumAmount());
		correction.setGrossSumAmount(invoiceProduct.getGrossSumAmount());
		correction.setVatAmount(invoiceProduct.getVatAmount());
		return correction;
	}
}
