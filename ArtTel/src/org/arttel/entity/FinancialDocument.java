package org.arttel.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class FinancialDocument<ProductType> implements UserSet {

	@Id
	@GeneratedValue
	@Column(name = "DocumentId")
	protected Integer documentId;

	@Column(name = "DocumentNumber")
	protected String documentNumber;

	@Column(name = "CreateDate")
	protected Date createDate;

	@Column(name = "PaymentDate")
	protected Date paymentDate;

	@Column(name = "Comments")
	protected String comments;

	@Column(name = "User")
	protected String user;

	@Column(name = "PaymentType")
	protected String paymentType;

	@Column(name = "DocumentStatus")
	protected String documentStatus;

	@Column(name = "SellerId")
	protected Integer sellerId;

	@Column(name = "AdditionalComments")
	protected String additionalComments;

	@ManyToOne
	@JoinColumn(name="clientId")
	protected Client client;

	@Column(name = "SellerBankAccountId")
	protected String sellerBankAccountId;

	@OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="documentId")
	private List<ProductType> documentProducts;

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

	public Integer getDocumentId() {
		return documentId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public List<ProductType> getDocumentProducts() {
		return documentProducts;
	}

	public String getDocumentStatus() {
		return documentStatus;
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

	public String getUser() {
		return user;
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

	public void setDocumentId(final Integer documentId) {
		this.documentId = documentId;
	}

	public void setDocumentNumber(final String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setDocumentProducts(final List<ProductType> documentProducts) {
		this.documentProducts = documentProducts;
	}

	public void setDocumentStatus(final String documentStatus) {
		this.documentStatus = documentStatus;
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

	@Override
	public void setUser(final String user) {
		this.user = user;
	}
}
