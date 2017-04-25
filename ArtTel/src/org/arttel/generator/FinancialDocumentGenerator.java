package org.arttel.generator;

import static org.arttel.util.PropertiesReader.PAPER_PRINT_ON;

import java.util.List;

import org.arttel.controller.vo.FinancialDocumentProductVO;
import org.arttel.controller.vo.FinancialDocumentVO;
import org.arttel.controller.vo.InvoiceValuesVO;
import org.arttel.dao.SellerBankAccountDao;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.VatRate;
import org.arttel.entity.SellerBankAccount;
import org.arttel.print.FilePrinter;
import org.arttel.util.PropertiesReader;
import org.arttel.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public abstract class FinancialDocumentGenerator<VO extends FinancialDocumentVO<?>> {

	@Autowired
	private SellerBankAccountDao sellerBankAccountDao;

	@Autowired
	private FilePrinter filePrinter;

	protected String compactResults(final List<String> filePaths, final String sessionId) {
		if(filePaths.size() > 1){
			return zip(filePaths, sessionId);
		} else if(filePaths.size() == 1) {
			return Iterables.getOnlyElement(filePaths);
		} else {
			return null;
		}
	}

	protected int countVatDetailsToInsert(final List<InvoiceValuesVO> vatDetailsValues) {
		final int vatDetailsToInsert = vatDetailsValues.size() - 4;
		if(vatDetailsToInsert < 0){
			return 0;
		} else {
			return vatDetailsToInsert;
		}
	}

	protected abstract Optional<String> getenratePdf(final String sessionId, final VO document);

	protected <T extends FinancialDocumentProductVO > String getReversedTaxRecords(final List<T> products) {
		final List<Integer> reversedTaxRecords = Lists.newArrayList();
		for(int i = 0; i < products.size(); i++){
			final VatRate vatRate = products.get(i).getProductDefinition().getVatRate();
			if(vatRate == VatRate.VAT_ROZLICZA_NABYWCA){
				reversedTaxRecords.add(i + 1);
			}
		}
		if(!reversedTaxRecords.isEmpty()){
			return "Obci¹¿enie odwrotne dla pozycji: ".concat(Joiner.on(", ").join(reversedTaxRecords));
		} else {
			return "";
		}
	}

	protected String getVatRatePrintValue(final VatRate vatRate) {
		if(vatRate == VatRate.VAT_ROZLICZA_NABYWCA){
			return "-";
		} else {
			return vatRate.getDesc();
		}
	}

	protected boolean isPaperPrintOn() {
		final String isPaperPrintOn = PropertiesReader.getProperty(PAPER_PRINT_ON);
		return Boolean.parseBoolean(isPaperPrintOn);
	}

	public Optional<String> printDocuments(final List<VO> documents, final String sessionId) {
		final List<String> filePaths = Lists.newArrayList();
		for(final VO document : documents){
			supplySpecificData(document);
			final Optional<String> filePath = getenratePdf(sessionId, document);
			if(filePath.isPresent()){
				if(isPaperPrintOn()){
					filePrinter.printFile(filePath.get());
				}
				filePaths.add(filePath.get());
				updateStatus(document);
			}
		}
		return Optional.fromNullable(compactResults(filePaths, sessionId));
	}

	protected abstract void setPendingStatus(final String documentId);

	protected void supplySpecificData(final VO document) {
		final SellerBankAccount bankAccount = sellerBankAccountDao.getBankAccountById(document.getSellerBankAccountId());
		document.setBankAccountName(bankAccount.getBankName());
		document.setBankAccountNumber(bankAccount.getAccountNumber());
	}

	protected void updateStatus(final VO document) {
		final String documentId = document.getDocumentId();
		final InvoiceStatus currentStatus = document.getStatus();
		if(currentStatus == InvoiceStatus.DRAFT){
			setPendingStatus(documentId);
			document.setStatus(InvoiceStatus.PENDING);
		}
	}

	private String zip(final List<String> filePaths, final String sessionId) {
		final String zipFilePath = FileGenerator.DOWNLOAD_PATH + sessionId + "/documents.zip";
		ZipUtil.zipFiles(filePaths, zipFilePath);
		return zipFilePath;
	}
}
