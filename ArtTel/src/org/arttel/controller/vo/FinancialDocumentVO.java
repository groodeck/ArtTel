package org.arttel.controller.vo;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.util.Translator;

public abstract class FinancialDocumentVO<Product extends FinancialDocumentProductVO> extends BasePageVO {

	private InvoiceFilterVO documentFilter = new InvoiceFilterVO();

	private String documentId;
	private String number;
	private String clientId;
	private String sellerId;
	private String clientDesc;
	private Date createDate;
	private Date paymentDate;
	private String user;
	private String comments;
	private String additionalComments;
	private PaymentType paymentType;
	private boolean editable;
	private InvoiceStatus status;
	private String sellerBankAccountId;
	private SellerVO seller;
	private ClientVO client;

	public abstract void addNewProduct();

	public abstract void clearProductList();

	public String getAdditionalComments() {
		return additionalComments;
	}

	public ClientVO getClient() {
		return client;
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

	public Date getCreateDate() {
		return createDate;
	}

	public InvoiceFilterVO getDocumentFilter() {
		return documentFilter;
	}

	public String getDocumentId() {
		return documentId;
	}

	public abstract List<Product> getDocumentProducts();

	@Override
	public Integer getId() {
		return Translator.parseInteger(getDocumentId());
	}

	public String getNumber() {
		return number;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public abstract Product getProduct(final int index);

	public SellerVO getSeller() {
		return seller;
	}

	public String getSellerBankAccountId() {
		return sellerBankAccountId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	@Override
	public String getUser() {
		return user;
	}

	public abstract boolean hasCorrection();

	public boolean isEditable() {
		return editable;
	}

	public void populate(final HttpServletRequest request) {
		documentId = request.getParameter("documentId");
		number = request.getParameter("number");
		sellerId = request.getParameter("sellerId");
		clientId = request.getParameter("clientId");
		createDate = Translator.parseDate(request.getParameter("createDate"), null);
		paymentDate = Translator.parseDate(request.getParameter("paymentDate"), null);
		comments = request.getParameter("comments");
		additionalComments = request.getParameter("additionalComments");
		final String paymentTypeIdn = request.getParameter("paymentType");
		if(StringUtils.isNotEmpty(paymentTypeIdn)){
			paymentType = PaymentType.getValueByIdn(paymentTypeIdn);
		}
		sellerBankAccountId = request.getParameter("sellerBankAccountId");
	}

	public void setAdditionalComments(final String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public void setClient(final ClientVO client) {
		this.client = client;
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

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public void setDocumentFilter(final InvoiceFilterVO documentFilter) {
		this.documentFilter = documentFilter;
	}

	public void setDocumentId(final String invoiceId) {
		this.documentId = invoiceId;
	}

	@Override
	protected void setEditable(final boolean editable) {
		this.editable = editable;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setPaymentDate(final Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setPaymentType(final PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public void setSeller(final SellerVO seller) {
		this.seller = seller;
	}

	public void setSellerBankAccountId(final String sellerBankAccountId) {
		this.sellerBankAccountId = sellerBankAccountId;
	}

	public void setSellerId(final String sellerId) {
		this.sellerId = sellerId;
	}

	public void setStatus(final InvoiceStatus status) {
		this.status = status;
	}

	public void setUser(final String user) {
		this.user = user;
	}
}
