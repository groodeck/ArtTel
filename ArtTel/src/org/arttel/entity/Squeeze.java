package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Squeeze")
public class Squeeze implements UserSet {

	@Id
	@GeneratedValue
	@Column(name="SqueezeId")
	private Integer squeezeId;

	@Column(name="City")
	private String city;

	@Column(name="Quantity")
	private Integer quantity;

	@Column(name="Meters")
	private Integer meters;

	@Column(name="UserId")
	private String userId;

	@Column(name="DealingId")
	private Integer dealingId;

	@Column(name="Price")
	private BigDecimal price;

	public String getCity() {
		return city;
	}

	public Integer getDealingId() {
		return dealingId;
	}

	public Integer getMeters() {
		return meters;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getSqueezeId() {
		return squeezeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setDealingId(final Integer dealingId) {
		this.dealingId = dealingId;
	}

	public void setMeters(final Integer meters) {
		this.meters = meters;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	public void setSqueezeId(final Integer squeezeId) {
		this.squeezeId = squeezeId;
	}

	@Override
	public void setUser(final String user) {
		userId = user;
	}

}
