package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="City")
public class City {

	@Id
	@Column(name="CityId")
	private Integer cityId; 
	
	@Column(name="CityIdn")
	private String cityIdn; 
	
	@Column(name="CityDesc")
	private String cityDesc; 
	
	@Column(name="ForInstalation")
	private Boolean forInstalation; 
	
	@Column(name="ForOrder")
	private Boolean forOrder; 
	
	@Column(name="ForReport")
	private Boolean forReport; 
	
	@Column(name="ForSqueeze")
	private Boolean forSqueeze; 
	
	@Column(name="ForDealing")
	private Boolean forDealing; 
	
	@Column(name="ForAgreement")
	private Boolean forAgreement; 
	
	@Column(name="ForInvoice")
	private Boolean forInvoice;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityIdn() {
		return cityIdn;
	}

	public void setCityIdn(String cityIdn) {
		this.cityIdn = cityIdn;
	}

	public String getCityDesc() {
		return cityDesc;
	}

	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}

	public Boolean getForInstalation() {
		return forInstalation;
	}

	public void setForInstalation(Boolean forInstalation) {
		this.forInstalation = forInstalation;
	}

	public Boolean getForOrder() {
		return forOrder;
	}

	public void setForOrder(Boolean forOrder) {
		this.forOrder = forOrder;
	}

	public Boolean getForReport() {
		return forReport;
	}

	public void setForReport(Boolean forReport) {
		this.forReport = forReport;
	}

	public Boolean getForSqueeze() {
		return forSqueeze;
	}

	public void setForSqueeze(Boolean forSqueeze) {
		this.forSqueeze = forSqueeze;
	}

	public Boolean getForDealing() {
		return forDealing;
	}

	public void setForDealing(Boolean forDealing) {
		this.forDealing = forDealing;
	}

	public Boolean getForAgreement() {
		return forAgreement;
	}

	public void setForAgreement(Boolean forAgreement) {
		this.forAgreement = forAgreement;
	}

	public Boolean getForInvoice() {
		return forInvoice;
	}

	public void setForInvoice(Boolean forInvoice) {
		this.forInvoice = forInvoice;
	}
	
	
}
