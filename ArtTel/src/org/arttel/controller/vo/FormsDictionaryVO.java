package org.arttel.controller.vo;

public abstract class FormsDictionaryVO extends BasePageVO {

	private boolean forInstalation;
	private boolean forOrder;
	private boolean forReport;
	private boolean forSqueeze;
	private boolean forDealing;
	private boolean forInvoice;

	public boolean isForDealing() {
		return forDealing;
	}
	public boolean isForInstalation() {
		return forInstalation;
	}
	public boolean isForInvoice() {
		return forInvoice;
	}
	public boolean isForOrder() {
		return forOrder;
	}
	public boolean isForReport() {
		return forReport;
	}
	public boolean isForSqueeze() {
		return forSqueeze;
	}
	public void setForDealing(final boolean forDealing) {
		this.forDealing = forDealing;
	}
	public void setForInstalation(final boolean forInstalation) {
		this.forInstalation = forInstalation;
	}
	public void setForInvoice(final boolean forInvoice) {
		this.forInvoice = forInvoice;
	}
	public void setForOrder(final boolean forOrder) {
		this.forOrder = forOrder;
	}
	public void setForReport(final boolean forReport) {
		this.forReport = forReport;
	}
	public void setForSqueeze(final boolean forSqueeze) {
		this.forSqueeze = forSqueeze;
	}
}
