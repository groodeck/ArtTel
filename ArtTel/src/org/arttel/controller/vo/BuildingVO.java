package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

public class BuildingVO extends BasePageVO {
	
	private String osiedle ;
	private String punktOptyczny;
	private String administrator;
	private String ulica;
	private String miejscowosc;
	private String iloscMieszkan;
	private String uzwrotniony;
	private String typSieci;
	private String zmodernizowany;
	private String nieZmodernizowany;
	private String potrzebaModernizacji;
	private String zgoda;
	private String iloscKondygnacji;
	private String iloscPionow;
	private String iloscHP;
	private String przylaczeElektryczne;
	private String wejsciaDoBudynku;
	private String typWzmacniacza;
	private String nrSeryjny;
	private String producent;
	private String poborMocy;
	private String czestotliwoscGorna;
	private String lokalizacjaWzmacniacza;
	private String sygnalWE;
	private String sygnalPierwszyWzm;
	private String dsWzmoc;
	private String dsKorek;
	private String dsWzmacniacz;
	private String kzKorekcja;
	private String kzWzmocnienie;
	private String gein;
	private String doprowadzenie;
	private String dojscieDobudynku;
	private String rgDoWzm;
	private String doSrodka;
	private String zakresModernizacjiPiwnica;
	private String zakresModernizacjiPiony;
	private String projekt;
	private String dataWystapienia;
	private String dataOtrzymania;
	private String wykonawca;
	private String nrZlecenia;
	private String dataZlecenia;
	private String planowanyOdbior;
	private String odbiorRzeczywisty;
	private String hpWZleceniu;
	private String dystrybucjaKanalBudynek;
	private String dystrybucjaBudynek;
	private String dystrybucjaRazem;
	private String magistrala;
	private String iloscGniazd;
	private String iloscAbVectra;
	private String iloscAbSocjal;
	private String iloscAbSocjalAdm;
	private String cenaAbSocjalAdm;
	private String zawartoscPakietu;
	private String ilecoAbKonkurent;
	private String cenaPakietuKonkurent;
	private String zawartoscPakietuKonkurent;
	private String uwagi;
	
	public String getOsiedle() {
		return osiedle;
	}

	public void setOsiedle(String osiedle) {
		this.osiedle = osiedle;
	}

	public String getPunktOptyczny() {
		return punktOptyczny;
	}

	public void setPunktOptyczny(String punktOptyczny) {
		this.punktOptyczny = punktOptyczny;
	}

	public String getAdministrator() {
		return administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getMiejscowosc() {
		return miejscowosc;
	}

	public void setMiejscowosc(String miejscowosc) {
		this.miejscowosc = miejscowosc;
	}

	public String getIloscMieszkan() {
		return iloscMieszkan;
	}

	public void setIloscMieszkan(String iloscMieszkan) {
		this.iloscMieszkan = iloscMieszkan;
	}

	public String getUzwrotniony() {
		return uzwrotniony;
	}

	public void setUzwrotniony(String uzwrotniony) {
		this.uzwrotniony = uzwrotniony;
	}

	public String getTypSieci() {
		return typSieci;
	}

	public void setTypSieci(String typSieci) {
		this.typSieci = typSieci;
	}

	public String getZmodernizowany() {
		return zmodernizowany;
	}

	public void setZmodernizowany(String zmodernizowany) {
		this.zmodernizowany = zmodernizowany;
	}

	public String getNieZmodernizowany() {
		return nieZmodernizowany;
	}

	public void setNieZmodernizowany(String nieZmodernizowany) {
		this.nieZmodernizowany = nieZmodernizowany;
	}

	public String getPotrzebaModernizacji() {
		return potrzebaModernizacji;
	}

	public void setPotrzebaModernizacji(String potrzebaModernizacji) {
		this.potrzebaModernizacji = potrzebaModernizacji;
	}

	public String getZgoda() {
		return zgoda;
	}

	public void setZgoda(String zgoda) {
		this.zgoda = zgoda;
	}

	public String getIloscKondygnacji() {
		return iloscKondygnacji;
	}

	public void setIloscKondygnacji(String iloscKondygnacji) {
		this.iloscKondygnacji = iloscKondygnacji;
	}

	public String getIloscPionow() {
		return iloscPionow;
	}

	public void setIloscPionow(String iloscPionow) {
		this.iloscPionow = iloscPionow;
	}

	public String getIloscHP() {
		return iloscHP;
	}

	public void setIloscHP(String iloscHP) {
		this.iloscHP = iloscHP;
	}

	public String getPrzylaczeElektryczne() {
		return przylaczeElektryczne;
	}

	public void setPrzylaczeElektryczne(String przylaczeElektryczne) {
		this.przylaczeElektryczne = przylaczeElektryczne;
	}

	public String getWejsciaDoBudynku() {
		return wejsciaDoBudynku;
	}

	public void setWejsciaDoBudynku(String wejsciaDoBudynku) {
		this.wejsciaDoBudynku = wejsciaDoBudynku;
	}

	public String getTypWzmacniacza() {
		return typWzmacniacza;
	}

	public void setTypWzmacniacza(String typWzmacniacza) {
		this.typWzmacniacza = typWzmacniacza;
	}

	public String getNrSeryjny() {
		return nrSeryjny;
	}

	public void setNrSeryjny(String nrSeryjny) {
		this.nrSeryjny = nrSeryjny;
	}

	public String getProducent() {
		return producent;
	}

	public void setProducent(String producent) {
		this.producent = producent;
	}

	public String getPoborMocy() {
		return poborMocy;
	}

	public void setPoborMocy(String poborMocy) {
		this.poborMocy = poborMocy;
	}

	public String getCzestotliwoscGorna() {
		return czestotliwoscGorna;
	}

	public void setCzestotliwoscGorna(String czestotliwoscGorna) {
		this.czestotliwoscGorna = czestotliwoscGorna;
	}

	public String getLokalizacjaWzmacniacza() {
		return lokalizacjaWzmacniacza;
	}

	public void setLokalizacjaWzmacniacza(String lokalizacjaWzmacniacza) {
		this.lokalizacjaWzmacniacza = lokalizacjaWzmacniacza;
	}

	public String getSygnalWE() {
		return sygnalWE;
	}

	public void setSygnalWE(String sygnalWE) {
		this.sygnalWE = sygnalWE;
	}

	public String getSygnalPierwszyWzm() {
		return sygnalPierwszyWzm;
	}

	public void setSygnalPierwszyWzm(String sygnalPierwszyWzm) {
		this.sygnalPierwszyWzm = sygnalPierwszyWzm;
	}

	public String getDsWzmoc() {
		return dsWzmoc;
	}

	public void setDsWzmoc(String dsWzmoc) {
		this.dsWzmoc = dsWzmoc;
	}

	public String getDsKorek() {
		return dsKorek;
	}

	public void setDsKorek(String dsKorek) {
		this.dsKorek = dsKorek;
	}

	public String getDsWzmacniacz() {
		return dsWzmacniacz;
	}

	public void setDsWzmacniacz(String dsWzmacniacz) {
		this.dsWzmacniacz = dsWzmacniacz;
	}

	public String getKzKorekcja() {
		return kzKorekcja;
	}

	public void setKzKorekcja(String kzKorekcja) {
		this.kzKorekcja = kzKorekcja;
	}

	public String getKzWzmocnienie() {
		return kzWzmocnienie;
	}

	public void setKzWzmocnienie(String kzWzmocnienie) {
		this.kzWzmocnienie = kzWzmocnienie;
	}

	public String getGein() {
		return gein;
	}

	public void setGein(String gein) {
		this.gein = gein;
	}

	public String getDoprowadzenie() {
		return doprowadzenie;
	}

	public void setDoprowadzenie(String doprowadzenie) {
		this.doprowadzenie = doprowadzenie;
	}

	public String getDojscieDobudynku() {
		return dojscieDobudynku;
	}

	public void setDojscieDobudynku(String dojscieDobudynku) {
		this.dojscieDobudynku = dojscieDobudynku;
	}

	public String getRgDoWzm() {
		return rgDoWzm;
	}

	public void setRgDoWzm(String rgDoWzm) {
		this.rgDoWzm = rgDoWzm;
	}

	public String getDoSrodka() {
		return doSrodka;
	}

	public void setDoSrodka(String doSrodka) {
		this.doSrodka = doSrodka;
	}

	public String getZakresModernizacjiPiwnica() {
		return zakresModernizacjiPiwnica;
	}

	public void setZakresModernizacjiPiwnica(String zakresModernizacjiPiwnica) {
		this.zakresModernizacjiPiwnica = zakresModernizacjiPiwnica;
	}

	public String getZakresModernizacjiPiony() {
		return zakresModernizacjiPiony;
	}

	public void setZakresModernizacjiPiony(String zakresModernizacjiPiony) {
		this.zakresModernizacjiPiony = zakresModernizacjiPiony;
	}

	public String getProjekt() {
		return projekt;
	}

	public void setProjekt(String projekt) {
		this.projekt = projekt;
	}

	public String getDataWystapienia() {
		return dataWystapienia;
	}

	public void setDataWystapienia(String dataWystapienia) {
		this.dataWystapienia = dataWystapienia;
	}

	public String getDataOtrzymania() {
		return dataOtrzymania;
	}

	public void setDataOtrzymania(String dataOtrzymania) {
		this.dataOtrzymania = dataOtrzymania;
	}

	public String getWykonawca() {
		return wykonawca;
	}

	public void setWykonawca(String wykonawca) {
		this.wykonawca = wykonawca;
	}

	public String getNrZlecenia() {
		return nrZlecenia;
	}

	public void setNrZlecenia(String nrZlecenia) {
		this.nrZlecenia = nrZlecenia;
	}

	public String getDataZlecenia() {
		return dataZlecenia;
	}

	public void setDataZlecenia(String dataZlecenia) {
		this.dataZlecenia = dataZlecenia;
	}

	public String getPlanowanyOdbior() {
		return planowanyOdbior;
	}

	public void setPlanowanyOdbior(String planowanyOdbior) {
		this.planowanyOdbior = planowanyOdbior;
	}

	public String getOdbiorRzeczywisty() {
		return odbiorRzeczywisty;
	}

	public void setOdbiorRzeczywisty(String odbiorRzeczywisty) {
		this.odbiorRzeczywisty = odbiorRzeczywisty;
	}

	public String getHpWZleceniu() {
		return hpWZleceniu;
	}

	public void setHpWZleceniu(String hpWZleceniu) {
		this.hpWZleceniu = hpWZleceniu;
	}

	public String getDystrybucjaKanalBudynek() {
		return dystrybucjaKanalBudynek;
	}

	public void setDystrybucjaKanalBudynek(String dystrybucjaKanalBudynek) {
		this.dystrybucjaKanalBudynek = dystrybucjaKanalBudynek;
	}

	public String getDystrybucjaBudynek() {
		return dystrybucjaBudynek;
	}

	public void setDystrybucjaBudynek(String dystrybucjaBudynek) {
		this.dystrybucjaBudynek = dystrybucjaBudynek;
	}

	public String getDystrybucjaRazem() {
		return dystrybucjaRazem;
	}

	public void setDystrybucjaRazem(String dystrybucjaRazem) {
		this.dystrybucjaRazem = dystrybucjaRazem;
	}

	public String getMagistrala() {
		return magistrala;
	}

	public void setMagistrala(String magistrala) {
		this.magistrala = magistrala;
	}

	public String getIloscGniazd() {
		return iloscGniazd;
	}

	public void setIloscGniazd(String iloscGniazd) {
		this.iloscGniazd = iloscGniazd;
	}

	public String getIloscAbVectra() {
		return iloscAbVectra;
	}

	public void setIloscAbVectra(String iloscAbVectra) {
		this.iloscAbVectra = iloscAbVectra;
	}

	public String getIloscAbSocjal() {
		return iloscAbSocjal;
	}

	public void setIloscAbSocjal(String iloscAbSocjal) {
		this.iloscAbSocjal = iloscAbSocjal;
	}

	public String getIloscAbSocjalAdm() {
		return iloscAbSocjalAdm;
	}

	public void setIloscAbSocjalAdm(String iloscAbSocjalAdm) {
		this.iloscAbSocjalAdm = iloscAbSocjalAdm;
	}

	public String getCenaAbSocjalAdm() {
		return cenaAbSocjalAdm;
	}

	public void setCenaAbSocjalAdm(String cenaAbSocjalAdm) {
		this.cenaAbSocjalAdm = cenaAbSocjalAdm;
	}

	public String getZawartoscPakietu() {
		return zawartoscPakietu;
	}

	public void setZawartoscPakietu(String zawartoscPakietu) {
		this.zawartoscPakietu = zawartoscPakietu;
	}

	public String getIlecoAbKonkurent() {
		return ilecoAbKonkurent;
	}

	public void setIlecoAbKonkurent(String ilecoAbKonkurent) {
		this.ilecoAbKonkurent = ilecoAbKonkurent;
	}

	public String getCenaPakietuKonkurent() {
		return cenaPakietuKonkurent;
	}

	public void setCenaPakietuKonkurent(String cenaPakietuKonkurent) {
		this.cenaPakietuKonkurent = cenaPakietuKonkurent;
	}

	public String getZawartoscPakietuKonkurent() {
		return zawartoscPakietuKonkurent;
	}

	public void setZawartoscPakietuKonkurent(String zawartoscPakietuKonkurent) {
		this.zawartoscPakietuKonkurent = zawartoscPakietuKonkurent;
	}

	public String getUwagi() {
		return uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	public void populate( final HttpServletRequest request ) {
		
		osiedle = request.getParameter("osiedle");
		punktOptyczny = request.getParameter("punktOptyczny");
		administrator = request.getParameter("administrator");
		ulica = request.getParameter("ulica");
		miejscowosc = request.getParameter("miejscowosc");
		iloscMieszkan = request.getParameter("iloscMieszkan");
		uzwrotniony = request.getParameter("uzwrotniony");
		typSieci = request.getParameter("typSieci");
		zmodernizowany = request.getParameter("zmodernizowany");
		nieZmodernizowany = request.getParameter("nieZmodernizowany");
		potrzebaModernizacji = request.getParameter("potrzebaModernizacji");
		zgoda = request.getParameter("zgoda");
		iloscKondygnacji = request.getParameter("iloscKondygnacji");
		iloscPionow = request.getParameter("iloscPionow");
		iloscHP = request.getParameter("iloscHP");
		przylaczeElektryczne = request.getParameter("przylaczeElektryczne");
		wejsciaDoBudynku = request.getParameter("wejsciaDoBudynku");
		typWzmacniacza = request.getParameter("typWzmacniacza");
		nrSeryjny = request.getParameter("nrSeryjny");
		producent = request.getParameter("producent");
		poborMocy = request.getParameter("poborMocy");
		czestotliwoscGorna = request.getParameter("czestotliwoscGorna");
		lokalizacjaWzmacniacza = request.getParameter("lokalizacjaWzmacniacza");
		sygnalWE = request.getParameter("sygnalWE");
		sygnalPierwszyWzm = request.getParameter("sygnalPierwszyWzm");
		dsWzmoc = request.getParameter("dsWzmoc");
		dsKorek = request.getParameter("dsKorek");
		dsWzmacniacz = request.getParameter("dsWzmacniacz");
		kzKorekcja = request.getParameter("kzKorekcja");
		kzWzmocnienie = request.getParameter("kzWzmocnienie");
		gein = request.getParameter("gein");
		doprowadzenie = request.getParameter("doprowadzenie");
		dojscieDobudynku = request.getParameter("dojscieDobudynku");
		rgDoWzm = request.getParameter("rgDoWzm");
		doSrodka = request.getParameter("doSrodka");
		zakresModernizacjiPiwnica = request.getParameter("zakresModernizacjiPiwnica");
		zakresModernizacjiPiony = request.getParameter("zakresModernizacjiPiony");
		projekt = request.getParameter("projekt");
		dataWystapienia = request.getParameter("dataWystapienia");
		dataOtrzymania = request.getParameter("dataOtrzymania");
		wykonawca = request.getParameter("wykonawca");
		nrZlecenia = request.getParameter("nrZlecenia");
		dataZlecenia = request.getParameter("dataZlecenia");
		planowanyOdbior = request.getParameter("planowanyOdbior");
		odbiorRzeczywisty = request.getParameter("odbiorRzeczywisty");
		hpWZleceniu = request.getParameter("hpWZleceniu");
		dystrybucjaKanalBudynek = request.getParameter("dystrybucjaKanalBudynek");
		dystrybucjaBudynek = request.getParameter("dystrybucjaBudynek");
		dystrybucjaRazem = request.getParameter("dystrybucjaRazem");
		magistrala = request.getParameter("magistrala");
		iloscGniazd = request.getParameter("iloscGniazd");
		iloscAbVectra = request.getParameter("iloscAbVectra");
		iloscAbSocjal = request.getParameter("iloscAbSocjal");
		iloscAbSocjalAdm = request.getParameter("iloscAbSocjalAdm");
		cenaAbSocjalAdm = request.getParameter("cenaAbSocjalAdm");
		zawartoscPakietu = request.getParameter("zawartoscPakietu");
		ilecoAbKonkurent = request.getParameter("ilecoAbKonkurent");
		cenaPakietuKonkurent = request.getParameter("cenaPakietuKonkurent");
		zawartoscPakietuKonkurent = request.getParameter("zawartoscPakietuKonkurent");
		uwagi = request.getParameter("uwagi");
		
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
