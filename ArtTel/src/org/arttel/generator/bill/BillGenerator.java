package org.arttel.generator.bill;

import org.arttel.controller.vo.BillVO;
import org.arttel.dao.BillDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.generator.FinancialDocumentGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

@Component
public class BillGenerator extends FinancialDocumentGenerator<BillVO> {

	@Autowired
	private BillFileGenerator billFileGenerator;

	@Autowired
	private BillDAO billDao;

	@Override
	protected Optional<String> getenratePdf(final String sessionId, final BillVO bill) {
		return billFileGenerator.generatePdf(bill, sessionId);
	}

	@Override
	protected void setPendingStatus(final String documentId) {
		billDao.setBillStatus(documentId, InvoiceStatus.PENDING);
	}
}
