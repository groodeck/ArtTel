package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CompanyCosts")
public class CompanyCosts {

	@Id
	@Column(name = "CompanyCostsId")
	private Integer companycostsId;

	@Column(name = "CompanyCostsIdn")
	private String companycostsIdn;

	@Column(name = "CompanyCostsDesc")
	private String companycostsDesc;

	@Column(name = "ForInstalation")
	private Boolean forInstalation;

	@Column(name = "ForOrder")
	private Boolean forOrder;

	@Column(name = "ForReport")
	private Boolean forReport;

	@Column(name = "ForSqueeze")
	private Boolean forSqueeze;

	@Column(name = "ForDealing")
	private Boolean forDealing;

	public Integer getCompanycostsId() {
		return companycostsId;
	}

	public void setCompanycostsId(Integer companycostsId) {
		this.companycostsId = companycostsId;
	}

	public String getCompanycostsIdn() {
		return companycostsIdn;
	}

	public void setCompanycostsIdn(String companycostsIdn) {
		this.companycostsIdn = companycostsIdn;
	}

	public String getCompanycostsDesc() {
		return companycostsDesc;
	}

	public void setCompanycostsDesc(String companycostsDesc) {
		this.companycostsDesc = companycostsDesc;
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
}
