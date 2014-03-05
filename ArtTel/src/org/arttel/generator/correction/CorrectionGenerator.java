package org.arttel.generator.correction;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.arttel.controller.vo.ClientVO;
import org.arttel.controller.vo.CorrectionVO;
import org.arttel.controller.vo.InvoceProductCorrectionVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceParticipant;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.InvoiceValuesVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.SellerVO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.SellerDAO;
import org.arttel.dictionary.PaymentType;
import org.arttel.dictionary.VatRate;
import org.arttel.exception.DaoException;
import org.arttel.generator.CellType;
import org.arttel.generator.DataCell;
import org.arttel.generator.DataSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorrectionGenerator {

	@Autowired
	private ClientDAO clientDao;

	@Autowired
	private SellerDAO sellerDao;

	private static final int PRODUCT_ROW_OFFSET = 15;
	private static final String OUTPUT_FILE_NAME = "Korekta.xlsx";
	private static final String TEMPLATE_XLS_NAME = "CorrectionTemplate.xlsx";

	public String generateCorrection(final InvoiceVO invoiceVO, final String sessionId) 
			throws DaoException, IOException, InvalidFormatException {

		final CorrectionVO correction = invoiceVO.getCorrection();
		final DataSheet dataSheet = new DataSheet();
		dataSheet.addDetailsCell(0, 11, new DataCell(correction.getCorrectionNumber(), CellType.TEXT));
		dataSheet.addDetailsCell(1, 11, new DataCell(invoiceVO.getNumber(), CellType.TEXT));
		dataSheet.addDetailsCell(2, 4, new DataCell("Data wystawienia " + correction.getCreateDate(), CellType.TEXT));
		dataSheet.addDetailsCell(3, 4, new DataCell("Data sprzeda¿y " + invoiceVO.getSignDate(), CellType.TEXT));
		
		final SellerVO seller = sellerDao.getSellerById(invoiceVO.getSellerId());
		dataSheet.addDetailsCell(6, 0, new DataCell(formatParticipantDescription(seller), CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(9, 0, new DataCell("NIP: "+seller.getNip(), CellType.TEXT));
		dataSheet.addDetailsCell(10, 0, new DataCell("Nr rachunku: "+seller.getBankName() + " \n " + seller.getAccountNumber(), 
				CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(10, 5, new DataCell(getPaymentTypeDescription(correction), CellType.WRAPABLE_TEXT));
		
		final ClientVO client = clientDao.getClientById(invoiceVO.getClientId());
		dataSheet.addDetailsCell(6, 5, new DataCell(formatParticipantDescription(client), CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(9, 5, new DataCell("NIP: "+client.getNip(), CellType.TEXT));

		int rowCounter = 0;
		for(final InvoceProductVO product : invoiceVO.getInvoiceProducts()){
			generateProductRow(product, dataSheet, rowCounter);
			rowCounter++;
		}
		final int productCount = invoiceVO.getInvoiceProducts().size()*3;
		
		dataSheet.addDetailsCell(15 + productCount, 8, new DataCell(getDouble(correction.getNetAmountDiff()), CellType.DOUBLE));
		dataSheet.addDetailsCell(15 + productCount, 11, new DataCell(getDouble(correction.getVatAmountDiff()), CellType.DOUBLE));
		dataSheet.addDetailsCell(15 + productCount, 13, new DataCell(getDouble(correction.getGrossAmountDiff()), CellType.DOUBLE));
		dataSheet.addDetailsCell(16 + productCount, 8, new DataCell(getDouble(invoiceVO.getNetAmount()), CellType.DOUBLE));
		dataSheet.addDetailsCell(16 + productCount, 11, new DataCell(getDouble(invoiceVO.getVatAmount()), CellType.DOUBLE));
		dataSheet.addDetailsCell(16 + productCount, 13, new DataCell(getDouble(invoiceVO.getGrossAmount()), CellType.DOUBLE));
		dataSheet.addDetailsCell(17 + productCount, 8, new DataCell(getDouble(correction.getNetAmount()), CellType.DOUBLE));
		dataSheet.addDetailsCell(17 + productCount, 11, new DataCell(getDouble(correction.getVatAmount()), CellType.DOUBLE));
		dataSheet.addDetailsCell(17 + productCount, 13, new DataCell(getDouble(correction.getGrossAmount()), CellType.DOUBLE));
		
		dataSheet.addDetailsCell(21 + productCount, 0, new DataCell(correction.getPaidWords(), CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(24 + productCount, 3, new DataCell(correction.getGrossAmountDiff(), CellType.WRAPABLE_TEXT));
		dataSheet.addDetailsCell(27 + productCount, 0, new DataCell("Uwagi: " + correction.getComments(), CellType.WRAPABLE_TEXT));
		
		generateVatDetailsValues(dataSheet, productCount, invoiceVO.getCorrectionDetailValueMap());
		
		return XlsCorrectionGenerator.generate(TEMPLATE_XLS_NAME, OUTPUT_FILE_NAME, dataSheet, sessionId);
	}

	private String getPaymentTypeDescription(final CorrectionVO correction) {
		final PaymentType paymentType = correction.getPaymentType();
		String result = "Sposób zap³aty: \n"+paymentType.getDesc();
		if(paymentType.isTransfer()){
			result = result.concat(". Termin p³atnoœci: " + correction.getPaymentDate());
		}
		return result;
	}

	private void generateVatDetailsValues(final DataSheet dataSheet, final int productCount, 
			final Map<VatRate, InvoiceValuesVO> invoiceDetailValueMap) {
		
		//Invoice detailed values (per VatRate)
		final InvoiceValuesVO rateZW = invoiceDetailValueMap.get(VatRate.VAT_ZW);
		dataSheet.addDetailsCell(18 + productCount, 8, new DataCell(rateZW.getNetAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(18 + productCount, 11, new DataCell(rateZW.getVatAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(18 + productCount, 13, new DataCell(getDouble(rateZW.getGrossAmount()), CellType.DOUBLE));
				
		final InvoiceValuesVO rate23 = invoiceDetailValueMap.get(VatRate.VAT_23);
		dataSheet.addDetailsCell(19 + productCount, 8, new DataCell(rate23.getNetAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(19 + productCount, 11, new DataCell(rate23.getVatAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(19 + productCount, 13, new DataCell(getDouble(rate23.getGrossAmount()), CellType.DOUBLE));

		final InvoiceValuesVO rate8 = invoiceDetailValueMap.get(VatRate.VAT_8);
		dataSheet.addDetailsCell(20 + productCount, 8, new DataCell(rate8.getNetAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(20 + productCount, 11, new DataCell(rate8.getVatAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(20 + productCount, 13, new DataCell(getDouble(rate8.getGrossAmount()), CellType.DOUBLE));
		
		final InvoiceValuesVO rate5 = invoiceDetailValueMap.get(VatRate.VAT_5);
		dataSheet.addDetailsCell(21 + productCount, 8, new DataCell(rate5.getNetAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(21 + productCount, 11, new DataCell(rate5.getVatAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(21 + productCount, 13, new DataCell(getDouble(rate5.getGrossAmount()), CellType.DOUBLE));
		
		final InvoiceValuesVO rate0 = invoiceDetailValueMap.get(VatRate.VAT_0);
		dataSheet.addDetailsCell(22 + productCount, 8, new DataCell(rate0.getNetAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(22 + productCount, 11, new DataCell(rate0.getVatAmount().doubleValue(), CellType.DOUBLE));
		dataSheet.addDetailsCell(22 + productCount, 13, new DataCell(getDouble(rate0.getGrossAmount()), CellType.DOUBLE));
	}

	private void generateProductRow(final InvoceProductVO product, final DataSheet dataSheet, 
			final int productNumber) {
		
		final InvoceProductCorrectionVO correction = product.getCorrection();
		dataSheet.setDataRowsOffset(PRODUCT_ROW_OFFSET);
		final List<DataCell> row = new ArrayList<DataCell>();
		row.add(new DataCell(productNumber+1, CellType.INT));
		row.add(new DataCell(getProductDescriptionIfDefined(product), CellType.TEXT));
		row.add(null);
		row.add(new DataCell("Przed korekt¹", CellType.TEXT));
		row.add(new DataCell(getUnitTypeIfDefined(product), CellType.TEXT));
		row.add(new DataCell(product.getQuantity(), CellType.TEXT));
		row.add(new DataCell(getDouble(getNetPriceIfDefined(product)), CellType.DOUBLE));
		row.add(null);
		row.add(new DataCell(getDouble(product.getNetSumAmount()), CellType.DOUBLE));
		row.add(null);
		row.add(new DataCell(getVatRateIfDefined(product), CellType.TEXT));
		row.add(new DataCell(getDouble(product.getVatAmount()), CellType.DOUBLE));
		row.add(null);
		row.add(new DataCell(getDouble(product.getGrossSumAmount()), CellType.DOUBLE));

		dataSheet.getRows().add(row);
		
		final List<DataCell> rowCorrected = new ArrayList<DataCell>();
		rowCorrected.add(null);
		rowCorrected.add(new DataCell(correction.getProductDefinition().getDesc(), CellType.TEXT));
		rowCorrected.add(null);
		rowCorrected.add(new DataCell("Po korekcie", CellType.TEXT));
		rowCorrected.add(new DataCell(correction.getProductDefinition().getUnitType().getDesc(), CellType.TEXT));
		rowCorrected.add(new DataCell(correction.getQuantity(), CellType.TEXT));
		rowCorrected.add(new DataCell(getDouble(correction.getProductDefinition().getNetPrice()), CellType.DOUBLE));
		rowCorrected.add(null);
		rowCorrected.add(new DataCell(getDouble(correction.getNetSumAmount()), CellType.DOUBLE));
		rowCorrected.add(null);
		rowCorrected.add(new DataCell(correction.getProductDefinition().getVatRate().getDesc(), CellType.TEXT));
		rowCorrected.add(new DataCell(getDouble(correction.getVatAmount()), CellType.DOUBLE));
		rowCorrected.add(null);
		rowCorrected.add(new DataCell(getDouble(correction.getGrossSumAmount()), CellType.DOUBLE));
		
		dataSheet.getRows().add(rowCorrected);
		
		final List<DataCell> correctDiff = new ArrayList<DataCell>();
		correctDiff.add(null);
		correctDiff.add(null);
		correctDiff.add(null);
		correctDiff.add(new DataCell("Korekta", CellType.TEXT));
		correctDiff.add(new DataCell(correction.getProductDefinition().getUnitType().getDesc(), CellType.TEXT));
		correctDiff.add(new DataCell(correction.getQuantityDiff(), CellType.TEXT));
		correctDiff.add(new DataCell(getDouble(correction.getProductDefinition().getNetPrice()), CellType.DOUBLE));
		correctDiff.add(null);
		correctDiff.add(new DataCell(getDouble(correction.getNetSumAmountDiff()), CellType.DOUBLE));
		correctDiff.add(null);
		correctDiff.add(new DataCell(correction.getProductDefinition().getVatRate().getDesc(), CellType.TEXT));
		correctDiff.add(new DataCell(getDouble(correction.getVatAmountDiff()), CellType.DOUBLE));
		correctDiff.add(null);
		correctDiff.add(new DataCell(getDouble(correction.getGrossSumAmountDiff()), CellType.DOUBLE));
		
		dataSheet.getRows().add(correctDiff);
	}

	private String getVatRateIfDefined(final InvoceProductVO product) {
		final ProductVO productDef = product.getProductDefinition();
		final String vatRate;
		if(productDef == null){
			vatRate = "";
		} else {
			vatRate = productDef.getVatRate().getDesc();
		}
		return vatRate;
	}

	private String getNetPriceIfDefined(final InvoceProductVO product) {
		final ProductVO productDef = product.getProductDefinition();
		final String netPrice;
		if(productDef==null){
			netPrice = "";
		}else {
			netPrice = productDef.getNetPrice();
		}
		return netPrice;
	}

	private String getProductDescriptionIfDefined(final InvoceProductVO product) {
		final ProductVO productDef = product.getProductDefinition();
		final String productDescription;
		if(productDef == null){
			productDescription = "";
		} else {
			productDescription = productDef.getDesc();
		}
		return productDescription;
	}

	private String getUnitTypeIfDefined(final InvoceProductVO product) {
		final ProductVO productDef = product.getProductDefinition();
		final String unitType;
		if(productDef == null){
			unitType = "";
		} else {
			unitType = productDef.getUnitType().getDesc();
		}
		return unitType;
	}

	private double getDouble(final String value) {
		if(isNotEmpty(value)){
			return new Double(value).doubleValue();
		}else{
			return 0;
		}
	}

	private String formatParticipantDescription(final InvoiceParticipant client) {
		final StringBuilder sb = new StringBuilder(client.getDesc() + " \n ");
		if(isNotEmpty(client.getZip())){
			sb.append(client.getZip() + " ");
		} 
		if(isNotEmpty(client.getCity())){
			sb.append(client.getCity() + " \n ");
		}
		if(isNotEmpty(client.getStreet())){
			sb.append(client.getStreet());
		}
		if(isNotEmpty(client.getHouse())){
			sb.append(" " + client.getHouse());
		} 
		if(isNotEmpty(client.getAppartment())){
			sb.append("/" + client.getAppartment());
		}
		return sb.toString();
	}
}
