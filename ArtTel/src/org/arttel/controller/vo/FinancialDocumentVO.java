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

	private InvoiceFilterVO invoiceFilter = new InvoiceFilterVO();

	private String invoiceId;
	private String number;
	private String clientId;
	private String sellerId;
	private String clientDesc;
	private Date createDate;
	private Date paymentDate;
	private String user;
	private String comments;
	private PaymentType paymentType;
	private boolean editable;
	private InvoiceStatus status;
	private String sellerBankAccountId;

	public abstract void addNewProduct();

	public abstract void clearProductList();

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

	public InvoiceFilterVO getInvoiceFilter() {
		return invoiceFilter;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public abstract List<Product> getInvoiceProducts();

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

	public boolean isEditable() {
		return editable;
	}

	public void populate(final HttpServletRequest request) {
		invoiceId = request.getParameter("invoiceId");
		number = request.getParameter("number");
		sellerId = request.getParameter("sellerId");
		clientId = request.getParameter("clientId");
		createDate = Translator.parseDate(request.getParameter("createDate"), null);
		paymentDate = Translator.parseDate(request.getParameter("paymentDate"), null);
		comments = request.getParameter("comments");
		final String paymentTypeIdn = request.getParameter("paymentType");
		if(StringUtils.isNotEmpty(paymentTypeIdn)){
			paymentType = PaymentType.getValueByIdn(paymentTypeIdn);
		}
		sellerBankAccountId = request.getParameter("sellerBankAccountId");
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

	public void setNumber(final String number) {
		this.number = number;
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

	public void setStatus(final InvoiceStatus status) {
		this.status = status;
	}

	public void setUser(final String user) {
		this.user = user;
	}
}
