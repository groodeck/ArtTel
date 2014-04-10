package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FundsEntry")
public class FundsEntry {

	@Id
	@GeneratedValue
	@Column(name = "FundsEntryId")
	private Integer fundsEntryId;
	
	@Column(name = "EntryAmount")
	private BigDecimal entryAmount;
	
	@Column(name = "EntryDate")
	private Date entryDate;
	
	@Column(name = "UserId")
	private String userId;
	
	@Column(name = "Comments")
	private String comments;

	public Integer getFundsEntryId() {
		return fundsEntryId;
	}

	public void setFundsEntryId(Integer fundsEntryId) {
		this.fundsEntryId = fundsEntryId;
	}

	public BigDecimal getEntryAmount() {
		return entryAmount;
	}

	public void setEntryAmount(BigDecimal entryAmount) {
		this.entryAmount = entryAmount;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
