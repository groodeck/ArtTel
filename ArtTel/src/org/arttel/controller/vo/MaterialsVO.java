package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

public class MaterialsVO extends BasePageVO {
	
	private String numerZlecenia;
	private String miasto;
	private String adres;
	private String nazwaPionu;	
	private String rodzaj;
	private String ilosc;
	private String jednostkaMiary;
	private String uzytkownikId;
	
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getIlosc() {
		return ilosc;
	}
	public void setIlosc(String ilosc) {
		this.ilosc = ilosc;
	}
	public String getJednostkaMiary() {
		return jednostkaMiary;
	}
	public void setJednostkaMiary(String jednostkaMiary) {
		this.jednostkaMiary = jednostkaMiary;
	}
	public String getNumerZlecenia() {
		return numerZlecenia;
	}
	public void setNumerZlecenia(String numerZlecenia) {
		this.numerZlecenia = numerZlecenia;
	}
	public String getMiasto() {
		return miasto;
	}
	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}
	public String getNazwaPionu() {
		return nazwaPionu;
	}
	public void setNazwaPionu(String nazwaPionu) {
		this.nazwaPionu = nazwaPionu;
	}
	public String getRodzaj() {
		return rodzaj;
	}
	public void setRodzaj(String rodzaj) {
		this.rodzaj = rodzaj;
	}
	public String getUzytkownikId() {
		return uzytkownikId;
	}
	public void setUzytkownikId(String uzytkownikId) {
		this.uzytkownikId = uzytkownikId;
	}

	public void populate( final HttpServletRequest request ) {

		numerZlecenia = request.getParameter("numerZlecenia");
		miasto = request.getParameter("miasto");
		adres = request.getParameter("adres");
		nazwaPionu = request.getParameter("nazwaPionu");
		rodzaj = request.getParameter("rodzaj");
		ilosc = request.getParameter("ilosc");
		jednostkaMiary = request.getParameter("jednostkaMiary");
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
}
