package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Payout")
public class Payout {

	@Id
	@GeneratedValue
	@Column(name = "PayoutId")
	private Integer payoutId;
	
	@Column(name = "NazwaTowaru")
	private String wareName;
	
	@Column(name = "Dokument")
	private String document;
	
	@Column(name = "Kwota")
	private BigDecimal amount;

	public Integer getPayoutId() {
		return payoutId;
	}

	public void setPayoutId(Integer payoutId) {
		this.payoutId = payoutId;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
