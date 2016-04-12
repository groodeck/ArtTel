package org.arttel.controller.vo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.arttel.generator.PrintableContent;
import org.arttel.ui.SortOrder;
import org.arttel.ui.SortableColumn;
import org.arttel.ui.TableHeader;
import org.arttel.util.Translator;

public class BillVO extends FinancialDocumentVO<BillProductVO> implements PrintableContent{

	public static final TableHeader resultTableHeader = new TableHeader(
			new SortableColumn("documentNumber", "concat("
					+ "substring(substring(concat('00000', b.documentNumber),length(concat('00000', b.documentNumber))-12, 13), 10, 4), "
					+ "substring(substring(concat('00000', b.documentNumber),length(concat('00000', b.documentNumber))-12, 13), 7, 2), "
					+ "substring(substring(concat('00000', b.documentNumber),length(concat('00000', b.documentNumber))-12, 13), 1, 5))", "Numer"),
					new SortableColumn("clientName", "b.client.clientDesc", "Klient", SortOrder.ASC),
					new SortableColumn("amount", "b.amount", "Kwota"),
					new SortableColumn("createDate", "b.createDate", "Data wystawienia"),
					new SortableColumn("paymentDate", "b.paymentDate", "Data p³atnoœci"),
					new SortableColumn("comments", "b.comments", "Uwagi"),
					new SortableColumn("documentStatus", "b.documentStatus", "Status"),
					new SortableColumn("user", "u.userName", "Wystawi³")
			);

	private List<BillProductVO> billProducts = new ArrayList<BillProductVO>();
	private String amount;
	private String bankAccountName;
	private String bankAccountNumber;
	private Date realizationDate;
	private SellerVO seller;
	private ClientVO client;

	@Override
	public void addNewProduct() {
		billProducts.add(new BillProductVO());
	}

	@Override
	public void clearProductList() {
		billProducts.clear();
	}

	public String getAmount() {
		return amount;
	}

	public String getAmountDecimalPart(){
		return Translator.getNumberParts(amount).getDecimalPart();
	}

	public String getAmountWholePart(){
		return Translator.getNumberParts(amount).getWholePart();
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public ClientVO getClient() {
		return client;
	}

	@Override
	public List<BillProductVO> getDocumentProducts() {
		return billProducts;
	}

	@Override
	public BillProductVO getProduct(final int index) {
		return billProducts.get(index);
	}

	public Date getRealizationDate() {
		return realizationDate;
	}

	public SellerVO getSeller() {
		return seller;
	}

	@Override
	public boolean hasCorrection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void populate(final HttpServletRequest request) {
		super.populate(request);
		realizationDate = Translator.parseDate(request.getParameter("realizationDate"), null);
		populateProducts(request);
	}

	private void populateProducts(final HttpServletRequest request) {
		final String prefix = "product";
		for(int i =0 ; i<billProducts.size(); i++){
			final BillProductVO product = billProducts.get(i);
			product.populate(request, prefix+"["+i+"].");
		}
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}

	public void setBankAccountName(final String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public void setBankAccountNumber(final String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public void setClient(final ClientVO client) {
		this.client = client;
	}

	public void setDocumentProducts(final List<BillProductVO> billProducts) {
		this.billProducts = billProducts;
	}

	public void setRealizationDate(final Date realizationDate) {
		this.realizationDate = realizationDate;
	}

	public void setSeller(final SellerVO seller) {
		this.seller = seller;
	}
}
