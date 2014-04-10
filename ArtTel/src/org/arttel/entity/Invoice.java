package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Invoice")
public class Invoice {

	@Id
	@GeneratedValue
	@Column(name = "InvoiceId")
	private Integer invoiceId;
	
	@Column(name = "InvoiceNumber")
	private String invoiceNumber;
	
	@Column(name = "ClientId")
	private Integer clientId;
	
	@Column(name = "CreateDate")
	private Date createDate;
	
	@Column(name = "SignatureDate")
	private Date signatureDate;
	
	@Column(name = "PaymentDate")
	private Date paymentDate;
	
	@Column(name = "NetAmount")
	private BigDecimal netAmount;
	
	@Column(name = "VatAmount")
	private BigDecimal vatAmount;
	
	@Column(name = "Comments")
	private String comments;
	
	@Column(name = "User")
	private String user;
	
	@Column(name = "Paid")
	private BigDecimal paid;
	
	@Column(name = "PaymentType")
	private String paymentType;
	
	@Column(name = "PaidWords")
	private String paidWords;
	
	@Column(name = "InvoiceStatus")
	private String invoiceStatus;
	
	@Column(name = "SellerId")
	private Integer sellerId;
	
	@Column(name = "AdditionalComments")
	private String additionalComments;

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getSignatureDate() {
		return signatureDate;
	}

	public void setSignatureDate(Date signatureDate) {
		this.signatureDate = signatureDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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

	public String getPaidWords() {
		return paidWords;
	}

	public void setPaidWords(String paidWords) {
		this.paidWords = paidWords;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getAdditionalComments() {
		return additionalComments;
	}

	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
	
}
