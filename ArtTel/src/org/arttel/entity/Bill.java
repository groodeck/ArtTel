package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Bill")
public class Bill extends FinancialDocument<BillProducts> {

	@Column(name = "Amount")
	private BigDecimal amount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setNetAmount(final BigDecimal netAmount) {
		amount = netAmount;
	}
}
