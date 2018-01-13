package org.arttel.generator.invoice;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.arttel.controller.vo.ClientVO;
import org.arttel.controller.vo.FinancialDocumentVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceParticipant;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.InvoiceValuesVO;
import org.arttel.controller.vo.SellerVO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.SellerBankAccountDao;
import org.arttel.dao.SellerDAO;
import org.arttel.dictionary.PaymentType;
import org.arttel.entity.SellerBankAccount;
import org.arttel.exception.DaoException;
import org.arttel.generator.CellType;
import org.arttel.generator.DataCell;
import org.arttel.generator.DataSheet;
import org.arttel.generator.FileGenerator;
import org.arttel.generator.FinancialDocumentGenerator;
import org.arttel.print.FilePrinter;
import org.arttel.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Component
public class InvoiceGeneratorOld extends FinancialDocumentGenerator {

	/*
	 * TODO: rewrite to freemarker like bills
	 */

	@Autowired
	private ClientDAO clientDao;

	@Autowired
	private SellerDAO sellerDao;

	@Autowired
	private SellerBankAccountDao bankAccountDao;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private FilePrinter printer;

	private static final int PRODUCT_ROW_OFFSET = 14;
	private static final String TEMPLATE_XLS_NAME = "InvoiceTemplate.xlsx";

	private String formatParticipantDescription(final InvoiceParticipant participant) {
		final StringBuilder sb = new StringBuilder(participant.getName() + " \n ");
		sb.append(participant.getAddressCity() + " \n ");
		sb.append(participant.getAddressStreet());
		return sb.toString();
	}

	public String generateInvoice(final InvoiceVO invoiceVO, final String sessionId)
			throws DaoException, IOException, InvalidFormatException {

		final DataSheet dataSheet = new DataSheet();
		dataSheet.addDetailsCell(0, 10, new DataCell(invoiceVO.getNumber(), CellType.TEXT));
		dataSheet.addDetailsCell(1,3, new DataCell("Data wystawienia " + invoiceVO.getCreateDate(), CellType.TEXT));
		dataSheet.addDetailsCell(2,3, new DataCell("Data sprzeda¿y " + invoiceVO.getSignDate(), CellType.TEXT));

		final SellerVO seller = sellerDao.getSellerById(invoiceVO.getSellerId());
		dataSheet.addDetailsCell(5, 0, new DataCell(formatParticipantDescription(seller), CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(8, 0, new DataCell("NIP: "+seller.getNip(), CellType.TEXT));
		final SellerBankAccount bankAccount = bankAccountDao.getBankAccountById(invoiceVO.getSellerBankAccountId());
		dataSheet.addDetailsCell(9, 0, new DataCell("Nr rachunku: "+bankAccount.getBankName() + " \n " + bankAccount.getAccountNumber(),
				CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(9, 5, new DataCell(getPaymentTypeDescription(invoiceVO), CellType.WRAPABLE_TEXT));

		final ClientVO client = clientDao.getClientVoById(invoiceVO.getClientId());
		dataSheet.addDetailsCell(5, 5, new DataCell(formatParticipantDescription(client), CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(8, 5, new DataCell("NIP: "+client.getNip(), CellType.TEXT));

		int rowCounter = 0;
		for(final InvoceProductVO product : invoiceVO.getDocumentProducts()){
			generateProductRow(product, dataSheet, rowCounter);
			rowCounter++;
		}
		final int productCount = invoiceVO.getDocumentProducts().size();

		dataSheet.addDetailsCell(14 + productCount, 7, new DataCell(getDouble(invoiceVO.getNetAmount()), CellType.DOUBLE));
		dataSheet.addDetailsCell(14 + productCount, 10, new DataCell(getDouble(invoiceVO.getVatAmount()), CellType.DOUBLE));
		dataSheet.addDetailsCell(14 + productCount, 12, new DataCell(getDouble(invoiceVO.getGrossAmount()), CellType.DOUBLE));
		dataSheet.addDetailsCell(15 + productCount, 5, new DataCell("W TYM", CellType.TEXT));

		final List<InvoiceValuesVO> vatDetailsValues = invoiceVO.getInvoiceDetailValues();
		generateVatDetailsValues(dataSheet, productCount, vatDetailsValues);

		final int vatDetailsToInsert = countVatDetailsToInsert(vatDetailsValues);
		dataSheet.addDetailsCell(17 + productCount + vatDetailsToInsert, 0, new DataCell(invoiceVO.getPaidWords(), CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(20 + productCount + vatDetailsToInsert, 2, new DataCell(invoiceVO.getGrossAmount(), CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(20 + productCount + vatDetailsToInsert, 6, new DataCell(getReversedTaxRecords(invoiceVO.getDocumentProducts()), CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(23 + productCount + vatDetailsToInsert, 0, new DataCell("Uwagi: " + invoiceVO.getComments(), CellType.WRAPABLE_TEXT));

		final String outputFileName = String.format("%s.xlsx", invoiceVO.getNumber().replace("/", "_"));;
		final String invoiceRelativePath = XlsInvoiceGenerator.generate(TEMPLATE_XLS_NAME, outputFileName, dataSheet, sessionId, vatDetailsToInsert);

		if(StringUtils.isNotEmpty(invoiceRelativePath)){
			updateStatus(invoiceVO);
		}
		return FileGenerator.BASE_DIR + invoiceRelativePath;
	}

	public Optional<String> generateInvoices(final List<InvoiceVO> invoices, final String sessionId) throws Exception {
		final List<String> invoiceGenerated = Lists.newArrayList();
		for(final InvoiceVO invoice : invoices){
			final String invoicePath = generateInvoice(invoice, sessionId);
			invoiceGenerated.add(invoicePath);
			//			TODO uncomment after rewrite to freemarker
			//			final String invoiceFileAbsolutePath = BaseXlsGenerator.BASE_DIR + "/" + invoiceRelativePath;
			//			printer.printFile(invoiceFileAbsolutePath);

		}
		return Optional.fromNullable(compactResults(invoiceGenerated, sessionId));
	}

	private void generateProductRow(final InvoceProductVO product, final DataSheet dataSheet,
			final int productNumber) {

		dataSheet.setDataRowsOffset(PRODUCT_ROW_OFFSET);
		final List<DataCell> row = new ArrayList<DataCell>();
		row.add(new DataCell(productNumber+1, CellType.INT));
		row.add(new DataCell(product.getProductDefinition().getDesc(), CellType.TEXT));
		row.add(new DataCell(product.getProductDefinition().getProductClassification(), CellType.TEXT));
		row.add(new DataCell(product.getProductDefinition().getUnitType().getDesc(), CellType.TEXT));
		row.add(new DataCell(product.getQuantity(), CellType.TEXT));
		row.add(new DataCell(getDouble(product.getProductDefinition().getNetPrice()), CellType.DOUBLE));
		row.add(null);
		row.add(new DataCell(getDouble(product.getNetSumAmount()), CellType.DOUBLE));
		row.add(null);
		row.add(new DataCell(getVatRatePrintValue(product.getProductDefinition().getVatRate()), CellType.TEXT));
		row.add(new DataCell(getDouble(product.getVatAmount()), CellType.DOUBLE));
		row.add(null);
		row.add(new DataCell(getDouble(product.getGrossSumAmount()), CellType.DOUBLE));

		dataSheet.getRows().add(row);
	}

	private void generateVatDetailsValues(final DataSheet dataSheet, final int productCount, final List<InvoiceValuesVO> vatRateDetails) {
		//Invoice detailed values (per VatRate)
		for(int i = 0 ; i< vatRateDetails.size(); i++){
			final InvoiceValuesVO singleRateDetails = vatRateDetails.get(i);
			dataSheet.addDetailsCell(15 + productCount + i, 7, new DataCell(singleRateDetails.getNetAmount().doubleValue(), CellType.DOUBLE));
			dataSheet.addDetailsCell(15 + productCount + i, 9, new DataCell(getVatRatePrintValue(singleRateDetails.getVatRate()), CellType.TEXT));
			dataSheet.addDetailsCell(15 + productCount + i, 10, new DataCell(singleRateDetails.getVatAmount().doubleValue(), CellType.DOUBLE));
			dataSheet.addDetailsCell(15 + productCount + i, 12, new DataCell(getDouble(singleRateDetails.getGrossAmount()), CellType.DOUBLE));
		}
	}

	private double getDouble(final String value) {
		if(isNotEmpty(value)){
			return new Double(value).doubleValue();
		}else{
			return 0;
		}
	}

	@Override
	protected Optional getenratePdf(final String sessionId, final FinancialDocumentVO invoice) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getPaymentTypeDescription(final InvoiceVO invoiceVO) {
		final PaymentType paymentType = invoiceVO.getPaymentType();
		String result = "Sposób zap³aty: \n"+paymentType.getDesc();
		if(paymentType.isTransfer()){
			result = result.concat(". Termin p³atnoœci: " + invoiceVO.getPaymentDate());
		}
		return result;
	}

	@Override
	protected void setPendingStatus(final String documentId) {
		invoiceService.setInvoicePending(documentId);
	}
}
