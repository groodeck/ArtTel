package org.arttel.controller.vo;

public class FormsDictionaryVO extends BasePageVO {

	private boolean forInstalation;
	private boolean forOrder;
	private boolean forReport;
	private boolean forSqueeze;
	private boolean forDealing;
	private boolean forInvoice;
	
	public boolean isForInstalation() {
		return forInstalation;
	}
	public void setForInstalation(boolean forInstalation) {
		this.forInstalation = forInstalation;
	}
	public boolean isForOrder() {
		return forOrder;
	}
	public void setForOrder(boolean forOrder) {
		this.forOrder = forOrder;
	}
	public boolean isForReport() {
		return forReport;
	}
	public void setForReport(boolean forReport) {
		this.forReport = forReport;
	}
	public boolean isForSqueeze() {
		return forSqueeze;
	}
	public void setForSqueeze(boolean forSqueeze) {
		this.forSqueeze = forSqueeze;
	}
	public boolean isForDealing() {
		return forDealing;
	}
	public void setForDealing(boolean forDealing) {
		this.forDealing = forDealing;
	}
	@Override
	protected String getUser() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void setEditable(boolean editable) {
		// TODO Auto-generated method stub
		
	}
	public boolean isForInvoice() {
		return forInvoice;
	}
	public void setForInvoice(boolean forInvoice) {
		this.forInvoice = forInvoice;
	}
}
