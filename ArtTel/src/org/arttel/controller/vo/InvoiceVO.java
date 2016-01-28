package org.arttel.controller.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.dictionary.VatRate;
import org.arttel.ui.SortOrder;
import org.arttel.ui.SortableColumn;
import org.arttel.ui.TableHeader;
import org.arttel.util.Translator;

import com.google.common.collect.Lists;

public class InvoiceVO extends BasePageVO{

	public static final TableHeader resultTableHeader = new TableHeader(
			new SortableColumn("invoiceNumber", "i.invoiceNumber", "Numer"),
			new SortableColumn("clientName", "i.client.clientDesc", "Klient", SortOrder.ASC),
			new SortableColumn("grossAmount", "i.netAmount+i.vatAmount", "Kwota brutto"),
			new SortableColumn("netAmount", "i.netAmount", "Kwota netto"),
			new SortableColumn("createDate", "i.createDate", "Data wystawienia"),
			new SortableColumn("paymentDate", "i.paymentDate", "Data p³atnoœci"),
			new SortableColumn("comments", "i.comments", "Uwagi"),
			new SortableColumn("invoiceStatus", "i.invoiceStatus", "Status"),
			new SortableColumn("user", "u.userName", "Wystawi³")
			);

	private InvoiceFilterVO invoiceFilter = new InvoiceFilterVO();

	private List<InvoceProductVO> invoiceProducts = new ArrayList<InvoceProductVO>();
	private String invoiceId;
	private String number;
	private String clientId;
	private String sellerId;
	private String clientDesc;
	private Date createDate;
	private Date signDate;
	private Date paymentDate;
	private String netAmount;
	private String vatAmount;
	private String user;
	private String comments;
	private String additionalComments;
	private String paid;
	private String paidWords;
	private PaymentType paymentType;
	private boolean editable;
	private InvoiceStatus status;
	private CorrectionVO correction;
	private String sellerBankAccountId;

	public void addNewInvoiceProduct() {
		invoiceProducts.add(new InvoceProductVO());
	}

	public void clearProductList() {
		invoiceProducts.clear();
	}

	public String getAdditionalComments() {
		return additionalComments;
	}

	public String getClientDesc() {
		return clientDesc;
	}


	public String getClientId() {
		return clientId;
	}

	public String getComments() {
		return comments;
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

	public Date getCreateDate() {
		return createDate;
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

	public InvoiceFilterVO getInvoiceFilter() {
		return invoiceFilter;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public List<InvoceProductVO> getInvoiceProducts() {
		return invoiceProducts;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public String getNumber() {
		return number;
	}

	public String getPaid() {
		return paid;
	}

	public String getPaidWords() {
		return paidWords;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getPaymentLeft() {
		final BigDecimal gross = Translator.getDecimal(getGrossAmount());
		final BigDecimal paid = Translator.getDecimal(getPaid());
		return gross.subtract(paid).toPlainString();
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public InvoceProductVO getProduct(final int index) {
		return invoiceProducts.get(index);
	}

	public String getSellerBankAccountId() {
		return sellerBankAccountId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public Date getSignDate() {
		return signDate;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	@Override
	public String getUser() {
		return user;
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

	public boolean isEditable() {
		return editable;
	}

	public void populate(final HttpServletRequest request) {
		invoiceId = request.getParameter("invoiceId");
		number = request.getParameter("number");
		sellerId = request.getParameter("sellerId");
		clientId = request.getParameter("clientId");
		createDate = Translator.parseDate(request.getParameter("createDate"), null);
		signDate = Translator.parseDate(request.getParameter("signDate"), null);
		paymentDate = Translator.parseDate(request.getParameter("paymentDate"), null);
		comments = request.getParameter("comments");
		additionalComments = request.getParameter("additionalComments");
		paid = Translator.parseDecimalStr(request.getParameter("paid"));
		paidWords = request.getParameter("paidWords");
		final String paymentTypeIdn = request.getParameter("paymentType");
		if(StringUtils.isNotEmpty(paymentTypeIdn)){
			paymentType = PaymentType.getValueByIdn(paymentTypeIdn);
		}
		sellerBankAccountId = request.getParameter("sellerBankAccountId");
		populateProducts(request);
	}

	private void populateProducts(final HttpServletRequest request) {
		// TODO Auto-generated method stub
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

	public void setClientDesc(final String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setCorrection(final CorrectionVO correction) {
		this.correction = correction;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	@Override
	protected void setEditable(final boolean editable) {
		this.editable = editable;
	}

	public void setInvoiceFilter(final InvoiceFilterVO invoiceFilter) {
		this.invoiceFilter = invoiceFilter;
	}

	public void setInvoiceId(final String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setInvoiceProducts(final List<InvoceProductVO> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public void setNetAmount(final String netAmount) {
		this.netAmount = netAmount;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setPaid(final String paid) {
		this.paid = paid;
	}

	public void setPaidWords(final String paidWords) {
		this.paidWords = paidWords;
	}

	public void setPaymentDate(final Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setPaymentType(final PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public void setSellerBankAccountId(final String sellerBankAccountId) {
		this.sellerBankAccountId = sellerBankAccountId;
	}

	public void setSellerId(final String sellerId) {
		this.sellerId = sellerId;
	}

	public void setSignDate(final Date signDate) {
		this.signDate = signDate;
	}

	public void setStatus(final InvoiceStatus status) {
		this.status = status;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public void setVatAmount(final String vatAmount) {
		this.vatAmount = vatAmount;
	}
}
