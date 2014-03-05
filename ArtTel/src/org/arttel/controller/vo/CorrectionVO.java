package org.arttel.controller.vo;

import java.math.BigDecimal;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.arttel.dictionary.PaymentType;
import org.arttel.util.Translator;

public class CorrectionVO extends BasePageVO {

	private String correctionId;
	private String invoiceId;
	private String correctionNumber;
	private Date createDate;
	private String netAmount;
	private String vatAmount;
	private String netAmountDiff;
	private String vatAmountDiff;
	private String grossAmountDiff;
	private String user;
	private String comments;
	private String paid;
	private String paidWords;
	private Date paymentDate;
	private PaymentType paymentType;
	
	private String invoiceNumber;
	private boolean editable;
	
	public String getCorrectionId() {
		return correctionId;
	}
	public void setCorrectionId(String correctionId) {
		this.correctionId = correctionId;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getCorrectionNumber() {
		return correctionNumber;
	}
	public void setCorrectionNumber(String correctionNumber) {
		this.correctionNumber = correctionNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getPaidWords() {
		return paidWords;
	}
	public void setPaidWords(String paidWords) {
		this.paidWords = paidWords;
	}
	public String getGrossAmount() {
		final BigDecimal net = Translator.getDecimal(netAmount);
		final BigDecimal vat = Translator.getDecimal(vatAmount);
		return net.add(vat).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
	}
	
	public String getPaymentLeft() {
		final BigDecimal gross = Translator.getDecimal(getGrossAmountDiff()); 
		final BigDecimal paid = Translator.getDecimal(getPaid());
		return gross.subtract(paid).toPlainString();
	}
	
	public void populate(HttpServletRequest request) {
		correctionNumber = request.getParameter("correctionNumber");
		createDate = Translator.parseDate(request.getParameter("correctionCreateDate"), null);
		comments = request.getParameter("comments");
		paid = Translator.parseDecimalStr(request.getParameter("paid"));
		final String paymentTypeIdn = request.getParameter("paymentType");
		if(StringUtils.isNotEmpty(paymentTypeIdn)){
			paymentType = PaymentType.getValueByIdn(paymentTypeIdn);
		}
	}
	public String getNetAmountDiff() {
		return netAmountDiff;
	}
	public void setNetAmountDiff(String netAmountDiff) {
		this.netAmountDiff = netAmountDiff;
	}
	public String getVatAmountDiff() {
		return vatAmountDiff;
	}
	public void setVatAmountDiff(String vatAmountDiff) {
		this.vatAmountDiff = vatAmountDiff;
	}
	public String getGrossAmountDiff() {
		return grossAmountDiff;
	}
	public void setGrossAmountDiff(String grossAmountDiff) {
		this.grossAmountDiff = grossAmountDiff;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	@Override
	protected void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return editable;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
}
