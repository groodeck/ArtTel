package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@ManyToOne
	@JoinColumn(name="clientId")
	private Client client;

	@Column(name = "SellerBankAccountId")
	private String sellerBankAccountId;

	@OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="invoiceId")
	private List<InvoiceProducts> invoiceProducts;

	public String getAdditionalComments() {
		return additionalComments;
	}

	public Client getClient() {
		return client;
	}

	public String getComments() {
		return comments;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public List<InvoiceProducts> getInvoiceProducts() {
		return invoiceProducts;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public String getPaidWords() {
		return paidWords;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public String getSellerBankAccountId() {
		return sellerBankAccountId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public Date getSignatureDate() {
		return signatureDate;
	}

	public String getUser() {
		return user;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setAdditionalComments(final String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public void setInvoiceId(final Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setInvoiceProducts(final List<InvoiceProducts> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public void setInvoiceStatus(final String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public void setNetAmount(final BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public void setPaid(final BigDecimal paid) {
		this.paid = paid;
	}

	public void setPaidWords(final String paidWords) {
		this.paidWords = paidWords;
	}

	public void setPaymentDate(final Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setPaymentType(final String paymentType) {
		this.paymentType = paymentType;
	}

	public void setSellerBankAccountId(final String sellerBankAccountId) {
		this.sellerBankAccountId = sellerBankAccountId;
	}

	public void setSellerId(final Integer sellerId) {
		this.sellerId = sellerId;
	}

	public void setSignatureDate(final Date signatureDate) {
		this.signatureDate = signatureDate;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public void setVatAmount(final BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

}
