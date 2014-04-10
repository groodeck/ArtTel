package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rate")
public class Rate {

	@Id
	@GeneratedValue
	@Column(name = "RateId")
	private Integer rateId;
	
	@Column(name = "Rodzaj")
	private String rateType;
	
	@Column(name = "Stawka")
	private BigDecimal rate;
	
	@Column(name = "JednostkaMiary")
	private String measurmentUnit;
	
	@Column(name = "UserId")
	private String userId;

	public Integer getRateId() {
		return rateId;
	}

	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getMeasurmentUnit() {
		return measurmentUnit;
	}

	public void setMeasurmentUnit(String measurmentUnit) {
		this.measurmentUnit = measurmentUnit;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
