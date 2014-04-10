package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Status")
public class Status {

	@Id
	@GeneratedValue
	@Column(name="StatusId")
	private Integer statusId;
	
	@Column(name="StatusIdn")
	private String statusIdn;
	
	@Column(name="StatusDesc")
	private String statusDesc;

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusIdn() {
		return statusIdn;
	}

	public void setStatusIdn(String statusIdn) {
		this.statusIdn = statusIdn;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
