package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "InvoceProductCorrection")
public class InvoiceProductCorrection {

	@Id
	@Column(name = "InvoceProductCorrectionId")
	private Integer invoceProductCorrectionId;
	
	@Column(name = "InvoceProductId")
	private Integer invoceProductId;
	
	@Column(name = "ProductId")
	private Integer productId;
	
	@Column(name = "InvoiceId")
	private Integer invoiceId;
	
	@Column(name = "Quantity")
	private BigDecimal quantity;
	
	@Column(name = "QuantityDiff")
	private BigDecimal quantityDiff;
	
	@Column(name = "NetSumAmount")
	private BigDecimal netSumAmount;
	
	@Column(name = "VatAmount")
	private BigDecimal vatAmount;
	
	@Column(name = "GrossSumAmount")
	private BigDecimal grossSumAmount;
	
	@Column(name = "NetSumAmountDiff")
	private BigDecimal netSumAmountDiff;
	
	@Column(name = "VatAmountDiff")
	private BigDecimal vatAmountDiff;
	
	@Column(name = "GrossSumAmountDiff")
	private BigDecimal grossSumAmountDiff;

	public Integer getInvoceProductCorrectionId() {
		return invoceProductCorrectionId;
	}

	public void setInvoceProductCorrectionId(Integer invoceProductCorrectionId) {
		this.invoceProductCorrectionId = invoceProductCorrectionId;
	}

	public Integer getInvoceProductId() {
		return invoceProductId;
	}

	public void setInvoceProductId(Integer invoceProductId) {
		this.invoceProductId = invoceProductId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getQuantityDiff() {
		return quantityDiff;
	}

	public void setQuantityDiff(BigDecimal quantityDiff) {
		this.quantityDiff = quantityDiff;
	}

	public BigDecimal getNetSumAmount() {
		return netSumAmount;
	}

	public void setNetSumAmount(BigDecimal netSumAmount) {
		this.netSumAmount = netSumAmount;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getGrossSumAmount() {
		return grossSumAmount;
	}

	public void setGrossSumAmount(BigDecimal grossSumAmount) {
		this.grossSumAmount = grossSumAmount;
	}

	public BigDecimal getNetSumAmountDiff() {
		return netSumAmountDiff;
	}

	public void setNetSumAmountDiff(BigDecimal netSumAmountDiff) {
		this.netSumAmountDiff = netSumAmountDiff;
	}

	public BigDecimal getVatAmountDiff() {
		return vatAmountDiff;
	}

	public void setVatAmountDiff(BigDecimal vatAmountDiff) {
		this.vatAmountDiff = vatAmountDiff;
	}

	public BigDecimal getGrossSumAmountDiff() {
		return grossSumAmountDiff;
	}

	public void setGrossSumAmountDiff(BigDecimal grossSumAmountDiff) {
		this.grossSumAmountDiff = grossSumAmountDiff;
	}
	
}
