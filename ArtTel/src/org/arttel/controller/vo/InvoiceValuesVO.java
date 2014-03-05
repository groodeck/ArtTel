package org.arttel.controller.vo;

import java.math.BigDecimal;

import org.arttel.dictionary.VatRate;

public class InvoiceValuesVO {

	private BigDecimal netAmount;
	private BigDecimal vatAmount;
	private VatRate vatRate;
	
	public InvoiceValuesVO(VatRate rate) {
		netAmount = BigDecimal.ZERO.setScale(2);
		vatAmount = BigDecimal.ZERO.setScale(2);
		vatRate = rate;
	}

	public String getGrossAmount() {
		return netAmount.add(vatAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public void addValue(final BigDecimal net, final BigDecimal vat, final VatRate vatRate){
		netAmount = netAmount.add(net).setScale(2, BigDecimal.ROUND_HALF_UP);
		vatAmount = vatAmount.add(vat).setScale(2, BigDecimal.ROUND_HALF_UP);
		this.vatRate = vatRate;
	}
	
	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public VatRate getVatRate() {
		return vatRate;
	}

	public void setVatRate(VatRate vat) {
		this.vatRate = vat;
	}
}
