package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class FinancialDocumentProduct {

	@Id
	@GeneratedValue
	@Column(name = "DocumentProductId")
	private Integer documentProductId;

	@Column(name = "DocumentId")
	private Integer documentId;

	@Column(name = "ProductId")
	private Integer productId;

	@Column(name = "Quantity")
	private BigDecimal quantity;

	public Integer getDocumentProductId() {
		return documentProductId;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public Integer getProductId() {
		return productId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setDocumentId(final Integer documentId) {
		this.documentId = documentId;
	}

	public void setDocumentProductId(final Integer documentProductId) {
		this.documentProductId = documentProductId;
	}

	public void setProductId(final Integer productId) {
		this.productId = productId;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

}
