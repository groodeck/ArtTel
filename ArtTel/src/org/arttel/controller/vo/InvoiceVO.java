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
import org.arttel.util.Translator;

import com.google.common.collect.Lists;

public class InvoiceVO extends BasePageVO{

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
	
	public void populate(HttpServletRequest request) {
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
		populateProducts(request);
	}
	
	private void populateProducts(HttpServletRequest request) {
		// TODO Auto-generated method stub
		final String prefix = "product";
		for(int i =0 ; i<invoiceProducts.size(); i++){
			InvoceProductVO product = invoiceProducts.get(i);
			product.populate(request, prefix+"["+i+"].");
		}
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	protected void setEditable(boolean editable) {
		this.editable = editable;
	}


	public void setUser(String user) {
		this.user = user;
	}

	public boolean isEditable() {
		return editable;
	}

	public InvoiceFilterVO getInvoiceFilter() {
		return invoiceFilter;
	}

	public void setInvoiceFilter(InvoiceFilterVO invoiceFilter) {
		this.invoiceFilter = invoiceFilter;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(String vatAmount) {
		this.vatAmount = vatAmount;
	}
	
	public InvoceProductVO getProduct(final int index) {
		return invoiceProducts.get(index);
	}

	public List<InvoceProductVO> getInvoiceProducts() {
		return invoiceProducts;
	}

	public void setInvoiceProducts(List<InvoceProductVO> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public void addNewInvoiceProduct() {
		invoiceProducts.add(new InvoceProductVO());
	}

	public void clearProductList() {
		invoiceProducts.clear();
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getClientDesc() {
		return clientDesc;
	}

	public void setClientDesc(String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public String getGrossAmount() {
		final BigDecimal net = Translator.getDecimal(netAmount);
		final BigDecimal vat = Translator.getDecimal(vatAmount);
		return net.add(vat).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
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
	
	public Map<VatRate, InvoiceValuesVO> getInvoiceDetailValueMap(){
		
		final Map<VatRate, InvoiceValuesVO> result = initializeInvoiceValuesMap();
		for(InvoceProductVO product : invoiceProducts){
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
	
	public Map<VatRate, InvoiceValuesVO> getCorrectionDetailValueMap(){
		
		final Map<VatRate, InvoiceValuesVO> result = initializeInvoiceValuesMap();
		for(InvoceProductVO product : invoiceProducts){
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

	private HashMap<VatRate, InvoiceValuesVO> initializeInvoiceValuesMap() {
		final HashMap<VatRate, InvoiceValuesVO> valuesMap = new HashMap<VatRate, InvoiceValuesVO>();
		for(final VatRate rate : VatRate.values()){
			valuesMap.put(rate, new InvoiceValuesVO(rate));
		}
		return valuesMap;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}
	
	public String getPaymentLeft() {
		final BigDecimal gross = Translator.getDecimal(getGrossAmount()); 
		final BigDecimal paid = Translator.getDecimal(getPaid());
		return gross.subtract(paid).toPlainString();
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaidWords() {
		return paidWords;
	}

	public void setPaidWords(String paidWords) {
		this.paidWords = paidWords;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}

	public CorrectionVO getCorrection() {
		return correction;
	}

	public void setCorrection(CorrectionVO correction) {
		this.correction = correction;
	}
	
	public boolean hasCorrection(){
		return getCorrection() != null;
	}

	public void prepareProductsCorrection() {
		final InvoceProductCorrectionVOFactory correctionFactory = new InvoceProductCorrectionVOFactory();
		for(final InvoceProductVO invoiceProduct : invoiceProducts){
			final InvoceProductCorrectionVO productCorrection = 
					correctionFactory.correctInvoiceProduct(invoiceProduct);
			invoiceProduct.setCorrection(productCorrection);
		}
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getAdditionalComments() {
		return additionalComments;
	}

	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
}
