package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "InvoceProducts")
public class InvoiceProducts extends FinancialDocumentProduct {

	@Column(name = "NetSumAmount")
	private BigDecimal netSumAmount;

	@Column(name = "VatAmount")
	private BigDecimal vatAmount;

	@Column(name = "GrossSumAmount")
	private BigDecimal grossSumAmount;

	public BigDecimal getGrossSumAmount() {
		return grossSumAmount;
	}

	public BigDecimal getNetSumAmount() {
		return netSumAmount;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setGrossSumAmount(final BigDecimal grossSumAmount) {
		this.grossSumAmount = grossSumAmount;
	}

	public void setNetSumAmount(final BigDecimal netSumAmount) {
		this.netSumAmount = netSumAmount;
	}

	public void setVatAmount(final BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

}
