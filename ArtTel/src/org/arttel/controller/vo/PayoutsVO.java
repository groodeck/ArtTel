package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

public class PayoutsVO extends BasePageVO {
	
	private String payoutId;
	private String nazwaTowaru;
	private String dokument;
	private String kwota;
	private String user;
	
	public String getNazwaTowaru() {
		return nazwaTowaru;
	}

	public void setNazwaTowaru(String nazwaTowaru) {
		this.nazwaTowaru = nazwaTowaru;
	}

	public String getDokument() {
		return dokument;
	}

	public void setDokument(String dokument) {
		this.dokument = dokument;
	}

	public String getKwota() {
		return kwota;
	}

	public void setKwota(String kwota) {
		this.kwota = kwota;
	}

	public void populate( final HttpServletRequest request ) {
		
		nazwaTowaru = request.getParameter("nazwaTowaru");
		dokument = request.getParameter("dokument");
		kwota = request.getParameter("kwota");
	}

	public String getPayoutId() {
		return payoutId;
	}

	public void setPayoutId(String payoutId) {
		this.payoutId = payoutId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	protected void setEditable(boolean editable) {
		// TODO Auto-generated method stub
		
	}

}
