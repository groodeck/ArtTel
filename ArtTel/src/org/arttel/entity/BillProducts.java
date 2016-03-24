package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BillProducts")
public class BillProducts extends FinancialDocumentProduct{

	@Column(name = "SumAmount")
	private BigDecimal sumAmount;

	@Column(name = "taxReleaseBasis")
	private String taxReleaseBasis;

	@Column(name = "productClassification")
	private String productClassification;

	public String getProductClassification() {
		return productClassification;
	}

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public String getTaxReleaseBasis() {
		return taxReleaseBasis;
	}

	public void setProductClassification(final String productClassification) {
		this.productClassification = productClassification;
	}

	public void setSumAmount(final BigDecimal netSumAmount) {
		sumAmount = netSumAmount;
	}

	public void setTaxReleaseBasis(final String taxReleaseBasis) {
		this.taxReleaseBasis = taxReleaseBasis;
	}
}
