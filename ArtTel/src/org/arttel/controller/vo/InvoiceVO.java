package org.arttel.controller.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.arttel.dictionary.VatRate;
import org.arttel.generator.PrintableContent;
import org.arttel.ui.SortOrder;
import org.arttel.ui.SortableColumn;
import org.arttel.ui.TableHeader;
import org.arttel.util.Translator;

import com.google.common.collect.Lists;

public class InvoiceVO extends FinancialDocumentVO<InvoceProductVO> implements PrintableContent{

	public static final TableHeader resultTableHeader = new TableHeader(
			new SortableColumn("documentNumber", "concat("
					+ "substring(substring(concat('00000', i.documentNumber),length(concat('00000', i.documentNumber))-12, 13), 10, 4), "
					+ "substring(substring(concat('00000', i.documentNumber),length(concat('00000', i.documentNumber))-12, 13), 7, 2), "
					+ "substring(substring(concat('00000', i.documentNumber),length(concat('00000', i.documentNumber))-12, 13), 1, 5))", "Numer"),
					new SortableColumn("clientName", "i.client.clientDesc", "Klient", SortOrder.ASC),
					new SortableColumn("grossAmount", "i.netAmount+i.vatAmount", "Kwota brutto"),
					new SortableColumn("netAmount", "i.netAmount", "Kwota netto"),
					new SortableColumn("createDate", "i.createDate", "Data wystawienia"),
					new SortableColumn("paymentDate", "i.paymentDate", "Data p³atnoœci"),
					new SortableColumn("comments", "i.comments", "Uwagi"),
					new SortableColumn("documentStatus", "i.documentStatus", "Status"),
					new SortableColumn("user", "u.userName", "Wystawi³")
			);

	private List<InvoceProductVO> invoiceProducts = new ArrayList<InvoceProductVO>();
	private Date signDate;
	private String netAmount;
	private String vatAmount;
	private String paid;
	private String paidWords;
	private CorrectionVO correction;
	private String city;

	@Override
	public void addNewProduct() {
		invoiceProducts.add(new InvoceProductVO());
	}

	@Override
	public void clearProductList() {
		invoiceProducts.clear();
	}

	public String getCity() {
		return city;
	}

	public CorrectionVO getCorrection() {
		return correction;
	}

	public Map<String, InvoiceValuesVO> getCorrectionDetailValueMap(){

		final Map<String, InvoiceValuesVO> result = new HashMap<String, InvoiceValuesVO>();
		for(final InvoceProductVO product : invoiceProducts){
			final InvoceProductCorrectionVO productCorrection = product.getCorrection();
			if(productCorrection != null
					&& productCorrection.getProductDefinition() != null){
				final VatRate vatRate = productCorrection.getProductDefinition().getVatRate();
				InvoiceValuesVO rateValues = result.get(vatRate.name());
				if(rateValues == null){
					rateValues = new InvoiceValuesVO(vatRate);
				}
				final BigDecimal productNetValue = Translator.getDecimal(productCorrection.getNetSumAmountDiff());
				final BigDecimal productVatValue = Translator.getDecimal(productCorrection.getVatAmountDiff());

				rateValues.addValue(productNetValue,  productVatValue, vatRate);
				result.put(vatRate.name(), rateValues);
			}
		}
		return result;
	}

	@Override
	public List<InvoceProductVO> getDocumentProducts() {
		return invoiceProducts;
	}

	public String getGrossAmount() {
		final BigDecimal net = Translator.getDecimal(netAmount);
		final BigDecimal vat = Translator.getDecimal(vatAmount);
		return net.add(vat).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public Map<String, InvoiceValuesVO> getInvoiceDetailValueMap(){

		final Map<String, InvoiceValuesVO> result = new HashMap<String, InvoiceValuesVO>();
		for(final InvoceProductVO product : invoiceProducts){
			if(product.getProductDefinition() != null){
				final VatRate vatRate = product.getProductDefinition().getVatRate();
				InvoiceValuesVO rateValues = result.get(vatRate.name());
				if(rateValues == null){
					rateValues = new InvoiceValuesVO(vatRate);
				}
				final BigDecimal productNetValue = Translator.getDecimal(product.getNetSumAmount());
				final BigDecimal productVatValue = Translator.getDecimal(product.getVatAmount());

				rateValues.addValue(productNetValue,  productVatValue, vatRate);
				result.put(vatRate.name(), rateValues);
			}
		}
		return result;
	}

	public List<InvoiceValuesVO> getInvoiceDetailValues(){

		final Map<String, InvoiceValuesVO> values;
		if(hasCorrection()){
			values = getCorrectionDetailValueMap();
		} else {
			values = getInvoiceDetailValueMap();
		}
		final List<InvoiceValuesVO> results = Lists.newArrayList();
		for(final VatRate vatRate : VatRate.values()){
			final InvoiceValuesVO rateValues = values.get(vatRate.name());
			if(rateValues != null){
				results.add(rateValues);
			}
		}
		return results;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public String getPaid() {
		return paid;
	}

	public String getPaidWords() {
		return paidWords;
	}

	public String getPaymentLeft() {
		final BigDecimal gross = Translator.getDecimal(getGrossAmount());
		final BigDecimal paid = Translator.getDecimal(getPaid());
		return gross.subtract(paid).toPlainString();
	}

	@Override
	public InvoceProductVO getProduct(final int index) {
		return invoiceProducts.get(index);
	}

	public Date getSignDate() {
		return signDate;
	}

	public String getVatAmount() {
		return vatAmount;
	}

	@Override
	public boolean hasCorrection(){
		return getCorrection() != null;
	}

	@Override
	public void populate(final HttpServletRequest request) {
		super.populate(request);
		signDate = Translator.parseDate(request.getParameter("signDate"), null);
		paid = Translator.parseDecimalStr(request.getParameter("paid"));
		paidWords = request.getParameter("paidWords");
		city = request.getParameter("city");
		populateProducts(request);
	}

	private void populateProducts(final HttpServletRequest request) {
		final String prefix = "product";
		for(int i =0 ; i<invoiceProducts.size(); i++){
			final InvoceProductVO product = invoiceProducts.get(i);
			product.populate(request, prefix+"["+i+"].");
		}
	}

	public void prepareProductsCorrection() {
		final InvoceProductCorrectionVOFactory correctionFactory = new InvoceProductCorrectionVOFactory();
		for(final InvoceProductVO invoiceProduct : invoiceProducts){
			final InvoceProductCorrectionVO productCorrection =
					correctionFactory.correctInvoiceProduct(invoiceProduct);
			invoiceProduct.setCorrection(productCorrection);
		}
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setCorrection(final CorrectionVO correction) {
		this.correction = correction;
	}

	public void setInvoiceProducts(final List<InvoceProductVO> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public void setNetAmount(final String netAmount) {
		this.netAmount = netAmount;
	}

	public void setPaid(final String paid) {
		this.paid = paid;
	}

	public void setPaidWords(final String paidWords) {
		this.paidWords = paidWords;
	}

	public void setSignDate(final Date signDate) {
		this.signDate = signDate;
	}

	public void setVatAmount(final String vatAmount) {
		this.vatAmount = vatAmount;
	}
}
