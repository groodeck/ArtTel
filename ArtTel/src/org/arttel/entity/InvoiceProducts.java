package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "InvoceProducts")
public class InvoiceProducts {

	@Id
	@Column(name = "InvoceProductId")
	private Integer invoceProductId;

	@Column(name = "InvoiceId")
	private Integer invoiceId;
	
	@Column(name = "ProductId")
	private Integer productId;
	
	@Column(name = "Quantity")
	private BigDecimal quantity;
	
	@Column(name = "NetSumAmount")
	private BigDecimal netSumAmount;
	
	@Column(name = "VatAmount")
	private BigDecimal vatAmount;
	
	@Column(name = "GrossSumAmount")
	private BigDecimal grossSumAmount;

	public Integer getInvoceProductId() {
		return invoceProductId;
	}

	public void setInvoceProductId(Integer invoceProductId) {
		this.invoceProductId = invoceProductId;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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
	
}
