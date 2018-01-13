package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Invoice")
public class Invoice extends FinancialDocument<InvoiceProducts> {

	@Column(name = "SignatureDate")
	private Date signatureDate;

	@Column(name = "NetAmount")
	private BigDecimal netAmount;

	@Column(name = "VatAmount")
	private BigDecimal vatAmount;

	@Column(name = "Paid")
	private BigDecimal paid;

	@Column(name = "PaidWords")
	private String paidWords;

	@Column(name = "City")
	private String city;

	public String getCity() {
		return city;
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

	public Date getSignatureDate() {
		return signatureDate;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setCity(final String city) {
		this.city = city;
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

	public void setSignatureDate(final Date signatureDate) {
		this.signatureDate = signatureDate;
	}

	public void setVatAmount(final BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

}
