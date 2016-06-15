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

	public String getComments() {
		return comments;
	}
	public String getCorrectionId() {
		return correctionId;
	}
	public String getCorrectionNumber() {
		return correctionNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public String getGrossAmount() {
		final BigDecimal net = Translator.getDecimal(netAmount);
		final BigDecimal vat = Translator.getDecimal(vatAmount);
		return net.add(vat).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
	}
	public String getGrossAmountDiff() {
		return grossAmountDiff;
	}
	@Override
	public Integer getId() {
		return Translator.parseInteger(getCorrectionId());
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public String getNetAmount() {
		return netAmount;
	}
	public String getNetAmountDiff() {
		return netAmountDiff;
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
		final BigDecimal gross = Translator.getDecimal(getGrossAmountDiff());
		final BigDecimal paid = Translator.getDecimal(getPaid());
		return gross.subtract(paid).toPlainString();
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	@Override
	public String getUser() {
		return user;
	}
	public String getVatAmount() {
		return vatAmount;
	}
	public String getVatAmountDiff() {
		return vatAmountDiff;
	}
	public boolean isEditable() {
		return editable;
	}
	public void populate(final HttpServletRequest request) {
		correctionNumber = request.getParameter("correctionNumber");
		createDate = Translator.parseDate(request.getParameter("correctionCreateDate"), null);
		comments = request.getParameter("comments");
		paid = Translator.parseDecimalStr(request.getParameter("paid"));
		final String paymentTypeIdn = request.getParameter("paymentType");
		if(StringUtils.isNotEmpty(paymentTypeIdn)){
			paymentType = PaymentType.getValueByIdn(paymentTypeIdn);
		}
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setCorrectionId(final String correctionId) {
		this.correctionId = correctionId;
	}
	public void setCorrectionNumber(final String correctionNumber) {
		this.correctionNumber = correctionNumber;
	}
	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}
	@Override
	protected void setEditable(final boolean editable) {
		this.editable = editable;
	}
	public void setGrossAmountDiff(final String grossAmountDiff) {
		this.grossAmountDiff = grossAmountDiff;
	}
	public void setInvoiceId(final String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public void setInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public void setNetAmount(final String netAmount) {
		this.netAmount = netAmount;
	}
	public void setNetAmountDiff(final String netAmountDiff) {
		this.netAmountDiff = netAmountDiff;
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
	public void setUser(final String user) {
		this.user = user;
	}
	public void setVatAmount(final String vatAmount) {
		this.vatAmount = vatAmount;
	}
	public void setVatAmountDiff(final String vatAmountDiff) {
		this.vatAmountDiff = vatAmountDiff;
	}

}
