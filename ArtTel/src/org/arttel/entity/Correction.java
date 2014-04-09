package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Correction")
public class Correction {

	@Id
	@Column(name = "CorrectionId")
	private Integer correctionId;
	
	@Column(name = "CorrectionNumber")
	private String correctionNumber;
	
	@Column(name = "InvoiceId")
	private Integer invoiceId;
	
	@Column(name = "CreateDate")
	private Date createDate;
	
	@Column(name = "NetAmount")
	private BigDecimal netAmount;
	
	@Column(name = "VatAmount")
	private BigDecimal vatAmount;
	
	@Column(name = "NetAmountDiff")
	private BigDecimal netAmountDiff;
	
	@Column(name = "VatAmountDiff")
	private BigDecimal vatAmountDiff;
	
	@Column(name = "Comments")
	private String comments;
	
	@Column(name = "User")
	private String user;
	
	@Column(name = "Paid")
	private BigDecimal paid;
	
	@Column(name = "PaymentType")
	private String paymentType;
	
	@Column(name = "PaymentDate")
	private Date paymentDate;
	
	@Column(name = "PaidWords")
	private String paidWords;

	public Integer getCorrectionId() {
		return correctionId;
	}

	public void setCorrectionId(Integer correctionId) {
		this.correctionId = correctionId;
	}

	public String getCorrectionNumber() {
		return correctionNumber;
	}

	public void setCorrectionNumber(String correctionNumber) {
		this.correctionNumber = correctionNumber;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getNetAmountDiff() {
		return netAmountDiff;
	}

	public void setNetAmountDiff(BigDecimal netAmountDiff) {
		this.netAmountDiff = netAmountDiff;
	}

	public BigDecimal getVatAmountDiff() {
		return vatAmountDiff;
	}

	public void setVatAmountDiff(BigDecimal vatAmountDiff) {
		this.vatAmountDiff = vatAmountDiff;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaidWords() {
		return paidWords;
	}

	public void setPaidWords(String paidWords) {
		this.paidWords = paidWords;
	}
	
}
