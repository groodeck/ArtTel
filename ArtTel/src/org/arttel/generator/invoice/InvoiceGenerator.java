package org.arttel.generator.invoice;

import org.arttel.controller.vo.InvoiceVO;
import org.arttel.generator.FinancialDocumentGenerator;
import org.arttel.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

@Component
public class InvoiceGenerator extends FinancialDocumentGenerator<InvoiceVO> {

	@Autowired
	private InvoiceFileGenerator invoiceFileGenerator;

	@Autowired
	private InvoiceService invoiceService;

	@Override
	protected Optional<String> getenratePdf(final String sessionId, final InvoiceVO invoice) {
		return invoiceFileGenerator.generatePdf(invoice, sessionId);
	}

	@Override
	protected void setPendingStatus(final String documentId) {
		invoiceService.setInvoicePending(documentId);
	}

}
