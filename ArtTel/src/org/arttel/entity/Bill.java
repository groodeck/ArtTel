package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Bill")
public class Bill extends FinancialDocument<BillProducts> {

	@Column(name = "Amount")
	private BigDecimal amount;

	@Column(name = "RealizationDate")
	private Date realizationDate;

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getRealizationDate() {
		return realizationDate;
	}

	public void setNetAmount(final BigDecimal netAmount) {
		amount = netAmount;
	}

	public void setRealizationDate(final Date realizationDate) {
		this.realizationDate = realizationDate;
	}
}
