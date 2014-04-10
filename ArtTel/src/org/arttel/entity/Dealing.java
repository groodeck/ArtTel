package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dealing")
public class Dealing {

	@Id
	@GeneratedValue
	@Column(name = "DealingId")
	private Integer dealingId;
	
	@Column(name = "DealingType")
	private String dealingType;
	
	@Column(name = "Date")
	private Date date;
	
	@Column(name = "CorporateCosts")
	private String corporateCosts;
	
	@Column(name = "PrivateCosts")
	private String privateCosts;
	
	@Column(name = "Income")
	private String income;
	
	@Column(name = "Fuel")
	private String fuel;
	
	@Column(name = "Amount")
	private BigDecimal amount;
	
	@Column(name = "Comments1")
	private String comments1;
	
	@Column(name = "Comments2")
	private String comments2;
	
	@Column(name = "Comments3")
	private String comments3;
	
	@Column(name = "UserId")
	private String userId;
	
	@Column(name = "FuelLiters")
	private BigDecimal fuelLiters;
	
	@Column(name = "Machine")
	private String machine;
	
	@Column(name = "City")
	private String city;

	public Integer getDealingId() {
		return dealingId;
	}

	public void setDealingId(Integer dealingId) {
		this.dealingId = dealingId;
	}

	public String getDealingType() {
		return dealingType;
	}

	public void setDealingType(String dealingType) {
		this.dealingType = dealingType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCorporateCosts() {
		return corporateCosts;
	}

	public void setCorporateCosts(String corporateCosts) {
		this.corporateCosts = corporateCosts;
	}

	public String getPrivateCosts() {
		return privateCosts;
	}

	public void setPrivateCosts(String privateCosts) {
		this.privateCosts = privateCosts;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getComments1() {
		return comments1;
	}

	public void setComments1(String comments1) {
		this.comments1 = comments1;
	}

	public String getComments2() {
		return comments2;
	}

	public void setComments2(String comments2) {
		this.comments2 = comments2;
	}

	public String getComments3() {
		return comments3;
	}

	public void setComments3(String comments3) {
		this.comments3 = comments3;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getFuelLiters() {
		return fuelLiters;
	}

	public void setFuelLiters(BigDecimal fuelLiters) {
		this.fuelLiters = fuelLiters;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
