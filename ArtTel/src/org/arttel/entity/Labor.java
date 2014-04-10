package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Labor")
public class Labor {

	@Id
	@GeneratedValue
	@Column(name = "LaborId")
	private Integer laborId;
	
	@Column(name = "NumerZlecenia")
	private String orderNumber;
	
	@Column(name = "Miasto")
	private String city;
	
	@Column(name = "Adres")
	private String address;
	
	@Column(name = "NazwaPionu")
	private String sectionName;
	
	@Column(name = "Rodzaj")
	private String laborType;
	
	@Column(name = "Ilosc")
	private Integer quantity;
	
	@Column(name = "JednostkaMiary")
	private String measurmentUnit;
	
	@Column(name = "UserId")
	private String userId;

	public Integer getLaborId() {
		return laborId;
	}

	public void setLaborId(Integer laborId) {
		this.laborId = laborId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getLaborType() {
		return laborType;
	}

	public void setLaborType(String laborType) {
		this.laborType = laborType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
