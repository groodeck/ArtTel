package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.arttel.dictionary.VatRate;

@Entity
@Table(name = "Product")
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "ProductId")
	private Integer productId;

	@Column(name = "ProductDescription")
	private String productDescription;

	@Column(name = "NetPrice")
	private BigDecimal netPrice;

	@Column(name = "Vat")
	@Enumerated(EnumType.STRING)
	private VatRate vat;

	@Column(name = "UnitType")
	private String unitType;

	@Column(name = "ProductClassification")
	private String productClassification;

	@Column(name = "Comments")
	private String comments;

	@Column(name = "User")
	private String user;

	public String getComments() {
		return comments;
	}

	public BigDecimal getNetPrice() {
		return netPrice;
	}

	public String getProductClassification() {
		return productClassification;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public Integer getProductId() {
		return productId;
	}

	public String getUnitType() {
		return unitType;
	}

	public String getUser() {
		return user;
	}

	public VatRate getVat() {
		return vat;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setNetPrice(final BigDecimal netPrice) {
		this.netPrice = netPrice;
	}

	public void setProductClassification(final String productClassification) {
		this.productClassification = productClassification;
	}

	public void setProductDescription(final String productDescription) {
		this.productDescription = productDescription;
	}

	public void setProductId(final Integer productId) {
		this.productId = productId;
	}

	public void setUnitType(final String unitType) {
		this.unitType = unitType;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public void setVat(final VatRate vat) {
		this.vat = vat;
	}

}
