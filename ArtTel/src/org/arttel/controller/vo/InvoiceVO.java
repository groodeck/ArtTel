package org.arttel.controller.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
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
			new SortableColumn("invoiceNumber", "i.invoiceNumber", "Numer"),
			new SortableColumn("clientName", "i.client.clientDesc", "Klient", SortOrder.ASC),
			new SortableColumn("grossAmount", "i.netAmount+i.vatAmount", "Kwota brutto"),
			new SortableColumn("netAmount", "i.netAmount", "Kwota netto"),
			new SortableColumn("createDate", "i.createDate", "Data wystawienia"),
			new SortableColumn("paymentDate", "i.paymentDate", "Data p�atno�ci"),
			new SortableColumn("comments", "i.comments", "Uwagi"),
			new SortableColumn("invoiceStatus", "i.invoiceStatus", "Status"),
			new SortableColumn("user", "u.userName", "Wystawi�")
			);

	private List<InvoceProductVO> invoiceProducts = new ArrayList<InvoceProductVO>();
	private Date signDate;
	private String netAmount;
	private String vatAmount;
	private String additionalComments;
	private String paid;
	private String paidWords;
	private CorrectionVO correction;

	@Override
	public void addNewProduct() {
		invoiceProducts.add(new InvoceProductVO());
	}

	@Override
	public void clearProductList() {
		invoiceProducts.clear();
	}

	public String getAdditionalComments() {
		return additionalComments;
	}

	public CorrectionVO getCorrection() {
		return correction;
	}

	public Map<VatRate, InvoiceValuesVO> getCorrectionDetailValueMap(){

		final Map<VatRate, InvoiceValuesVO> result = initializeInvoiceValuesMap();
		for(final InvoceProductVO product : invoiceProducts){
			final InvoceProductCorrectionVO productCorrection = product.getCorrection();
			if(productCorrection != null
					&& productCorrection.getProductDefinition() != null){
				final VatRate vatRate = productCorrection.getProductDefinition().getVatRate();
				final InvoiceValuesVO rateValues = result.get(vatRate);
				final BigDecimal productNetValue = Translator.getDecimal(productCorrection.getNetSumAmountDiff());
				final BigDecimal productVatValue = Translator.getDecimal(productCorrection.getVatAmountDiff());

				rateValues.addValue(productNetValue,  productVatValue, vatRate);
				result.put(vatRate, rateValues);
			}
		}
		return result;
	}

	public String getGrossAmount() {
		final BigDecimal net = Translator.getDecimal(netAmount);
		final BigDecimal vat = Translator.getDecimal(vatAmount);
		return net.add(vat).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public Map<VatRate, InvoiceValuesVO> getInvoiceDetailValueMap(){

		final Map<VatRate, InvoiceValuesVO> result = initializeInvoiceValuesMap();
		for(final InvoceProductVO product : invoiceProducts){
			if(product.getProductDefinition() != null){
				final VatRate vatRate = product.getProductDefinition().getVatRate();
				final InvoiceValuesVO rateValues = result.get(vatRate);
				final BigDecimal productNetValue = Translator.getDecimal(product.getNetSumAmount());
				final BigDecimal productVatValue = Translator.getDecimal(product.getVatAmount());

				rateValues.addValue(productNetValue,  productVatValue, vatRate);
				result.put(vatRate, rateValues);
			}
		}
		return result;
	}

	public Collection<InvoiceValuesVO> getInvoiceDetailValues(){

		final Map<VatRate, InvoiceValuesVO> values;
		if(hasCorrection()){
			values = getCorrectionDetailValueMap();
		} else {
			values = getInvoiceDetailValueMap();
		}
		return Lists.newArrayList(
				values.get(VatRate.VAT_ZW),
				values.get(VatRate.VAT_0),
				values.get(VatRate.VAT_5),
				values.get(VatRate.VAT_8),
				values.get(VatRate.VAT_23)) ;
	}

	@Override
	public List<InvoceProductVO> getInvoiceProducts() {
		return invoiceProducts;
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

	public boolean hasCorrection(){
		return getCorrection() != null;
	}

	private HashMap<VatRate, InvoiceValuesVO> initializeInvoiceValuesMap() {
		final HashMap<VatRate, InvoiceValuesVO> valuesMap = new HashMap<VatRate, InvoiceValuesVO>();
		for(final VatRate rate : VatRate.values()){
			valuesMap.put(rate, new InvoiceValuesVO(rate));
		}
		return valuesMap;
	}

	@Override
	public void populate(final HttpServletRequest request) {
		super.populate(request);
		signDate = Translator.parseDate(request.getParameter("signDate"), null);
		additionalComments = request.getParameter("additionalComments");
		paid = Translator.parseDecimalStr(request.getParameter("paid"));
		paidWords = request.getParameter("paidWords");
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

	public void setAdditionalComments(final String additionalComments) {
		this.additionalComments = additionalComments;
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
