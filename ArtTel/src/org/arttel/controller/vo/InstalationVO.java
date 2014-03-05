package org.arttel.controller.vo;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.controller.vo.filter.InstalationFilterVO;
import org.arttel.dictionary.InstalationType;
import org.arttel.dictionary.Status;
import org.arttel.util.Translator;
import org.arttel.util.WebUtils;

public class InstalationVO extends BasePageVO {
	
	private InstalationFilterVO instalationFilter = new InstalationFilterVO();
	private String selectedCity;
	
	private String instalationId;
	private Status status;
	private String nrTelefonu;
	private String imie;
	private String nazwisko;
	private String adres;
	private String city;
	private String nrSeryjny;
	private Date dataInstalacji;
	private InstalationType rodzajInstalacji;
	private Date dataPodpisaniaUmowy;
	private String pakiet;
	String user;
	private String macAdres;
	private String downstream;
	private String upstream;
	
	private String iloscGniazd;
	private String wykonaneInstalacje;
	private String iloscKabla;
	private String uwagi;
	private String dodatkoweUwagi;
	
	private boolean editable;
	
	public String getInstalationId() {
		return instalationId;
	}

	public void setInstalationId(String decodersId) {
		this.instalationId = decodersId;
	}
	
	public String getIloscGniazd() {
		return iloscGniazd;
	}

	public void setIloscGniazd(String iloscGniazd) {
		this.iloscGniazd = iloscGniazd;
	}

	public String getWykonaneInstalacje() {
		return wykonaneInstalacje;
	}

	public void setWykonaneInstalacje(String wykonaneInstalacje) {
		this.wykonaneInstalacje = wykonaneInstalacje;
	}

	public String getIloscKabla() {
		return iloscKabla;
	}

	public void setIloscKabla(String iloscKabla) {
		this.iloscKabla = iloscKabla;
	}

	public String getUwagi() {
		return uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	public String getDodatkoweUwagi() {
		return dodatkoweUwagi;
	}

	public void setDodatkoweUwagi(String dodatkoweUwagi) {
		this.dodatkoweUwagi = dodatkoweUwagi;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public InstalationType getRodzajInstalacji() {
		return rodzajInstalacji;
	}

	public void setRodzajInstalacji(InstalationType rodzajInstalacji) {
		this.rodzajInstalacji = rodzajInstalacji;
	}

	public String getPakiet() {
		return pakiet;
	}

	public void setPakiet(String pakiet) {
		this.pakiet = pakiet;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNrSeryjny() {
		return nrSeryjny;
	}

	public void setNrSeryjny(String nrSeryjny) {
		this.nrSeryjny = nrSeryjny;
	}
	
	public Date getDataInstalacji() {
		return dataInstalacji;
	}

	public void setDataInstalacji(Date dataInstalacji) {
		this.dataInstalacji = dataInstalacji;
	}

	public Date getDataPodpisaniaUmowy() {
		return dataPodpisaniaUmowy;
	}

	public void setDataPodpisaniaUmowy(Date dataPodpisaniaUmowy) {
		this.dataPodpisaniaUmowy = dataPodpisaniaUmowy;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public void populate( final HttpServletRequest request ) {
		
		instalationId = request.getParameter("instalationId");
		selectedCity = request.getParameter("selectedCity");
		
		final String statusStr = request.getParameter("status");
		if(statusStr != null){
			status = Status.getValueByIdn(statusStr);
		}
		final String instalationType = request.getParameter("rodzajInstalacji");
		if(instalationType != null){
			rodzajInstalacji = InstalationType.getValueByIdn(instalationType);
		}
		final String dataPodpisaniaUmowyStr = request.getParameter("dataPodpisaniaUmowy");
		dataPodpisaniaUmowy = Translator.parseDate(dataPodpisaniaUmowyStr, null);
		nrTelefonu = request.getParameter("nrTelefonu");
		imie = request.getParameter("imie");
		nazwisko = request.getParameter("nazwisko");
		adres = request.getParameter("adres");
		city = request.getParameter("city");
		pakiet = request.getParameter("pakiet");
		nrSeryjny = request.getParameter("nrSeryjny");
		final String dataInstalacjiStr = request.getParameter("dataInstalacji");
		dataInstalacji = Translator.parseDate(dataInstalacjiStr, null);
		iloscGniazd = Translator.emptyAsNull(request.getParameter("iloscGniazd"));
		wykonaneInstalacje = Translator.emptyAsNull(request.getParameter("wykonaneInstalacje"));
		iloscKabla = request.getParameter("iloscKabla");
		uwagi = request.getParameter("uwagi");
		dodatkoweUwagi = request.getParameter("dodatkoweUwagi");
		macAdres = request.getParameter("macAdres");
		final String downstreamStr = request.getParameter("downstream");
		downstream = Translator.parseDecimalStr(downstreamStr);
		final String upstreamStr = request.getParameter("upstream");
		upstream = Translator.parseDecimalStr(upstreamStr);
	}

	public String getMacAdres() {
		return macAdres;
	}

	public void setMacAdres(String macAdres) {
		this.macAdres = macAdres;
	}

	public String getDownstream() {
		return downstream;
	}

	public void setDownstream(String downstr) {
		this.downstream = downstr;
	}

	public String getUpstream() {
		return upstream;
	}

	public void setUpstream(String upstr) {
		this.upstream = upstr;
	}

	public InstalationFilterVO getInstalationFilter() {
		return instalationFilter;
	}

	public void setInstalationFilter(InstalationFilterVO instalationFilter) {
		this.instalationFilter = instalationFilter;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getDownstreamColor(){
		return WebUtils.getDownstreamLevelColor(downstream);
	}
	
	public String getUpstreamColor(){
		return WebUtils.getUpstreamLevelColor(upstream);
	}

	public String getNrTelefonu() {
		return nrTelefonu;
	}

	public void setNrTelefonu(String nrTelefonu) {
		this.nrTelefonu = nrTelefonu;
	}
	
	public boolean isClosable(){
		return getStatus() != Status.DONE;
	}

	public String getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(String selectedCity) {
		this.selectedCity = selectedCity;
	}
}
