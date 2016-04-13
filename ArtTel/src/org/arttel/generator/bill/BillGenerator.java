package org.arttel.generator.bill;

import java.util.List;

import org.arttel.controller.vo.BillVO;
import org.arttel.dao.BillDAO;
import org.arttel.dao.SellerBankAccountDao;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.entity.SellerBankAccount;
import org.arttel.generator.FinancialDocumentGenerator;
import org.arttel.print.FilePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Component
public class BillGenerator extends FinancialDocumentGenerator {

	@Autowired
	private BillFileGenerator billFileGenerator;

	@Autowired
	private SellerBankAccountDao sellerBankAccountDao;

	@Autowired
	private FilePrinter filePrinter;

	@Autowired
	private BillDAO billDao;

	public Optional<String> printDocuments(final List<BillVO> bills, final String sessionId) {
		final List<String> filePaths = Lists.newArrayList();
		for(final BillVO bill : bills){
			supplySpecificData(bill);
			final Optional<String> filePath = billFileGenerator.generatePdf(bill, sessionId);
			if(filePath.isPresent()){
				if(isPaperPrintOn()){
					filePrinter.printFile(filePath.get());
				}
				filePaths.add(filePath.get());
				updateStatus(bill);
			}
		}
		return Optional.fromNullable(compactResults(filePaths, sessionId));
	}

	@Override
	protected void setPendingStatus(final String documentId) {
		billDao.setBillStatus(documentId, InvoiceStatus.PENDING);
	}

	private void supplySpecificData(final BillVO bill) {
		final SellerBankAccount bankAccount = sellerBankAccountDao.getBankAccountById(bill.getSellerBankAccountId());
		bill.setBankAccountName(bankAccount.getBankName());
		bill.setBankAccountNumber(bankAccount.getAccountNumber());
	}
}
