package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Squeeze")
public class Squeeze {

	@Id
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

	public Integer getSqueezeId() {
		return squeezeId;
	}

	public void setSqueezeId(Integer squeezeId) {
		this.squeezeId = squeezeId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getMeters() {
		return meters;
	}

	public void setMeters(Integer meters) {
		this.meters = meters;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getDealingId() {
		return dealingId;
	}

	public void setDealingId(Integer dealingId) {
		this.dealingId = dealingId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
