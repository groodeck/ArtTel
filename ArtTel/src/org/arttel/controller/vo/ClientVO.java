package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.util.Translator;
import org.arttel.view.ComboElement;

public class ClientVO extends FormsDictionaryVO implements ComboElement, InvoiceParticipant  {

	private ClientFilterVO clientFilter = new ClientFilterVO(null, null);
	private String selectedClient;
	
	private String clientId;
	private String clientDesc;

	private String nip;
	
	private String city;
	private String street;
	private String house;
	private String appartment;
	private String zip;
	
	public ClientVO(String clientId, String clientDesc) {
		this.clientId = clientId;
		this.clientDesc = clientDesc;
	}
	
	public ClientVO(){
	}

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientDesc() {
		return clientDesc;
	}
	public void setClientDesc(String clientDesc) {
		this.clientDesc = clientDesc;
	}
	
	@Override
	public String getIdn() {
		return clientId;
	}
	@Override
	public String getDesc() {
		return clientDesc;
	}

	public void populate(HttpServletRequest request, String prefix) {
		clientId = request.getParameter("clientId");
		clientDesc = request.getParameter(prefix + "clientDesc");
		nip = request.getParameter(prefix + "nip");
		city = request.getParameter(prefix + "city");
		street = request.getParameter(prefix + "street");
		house = request.getParameter(prefix + "house");
		appartment = request.getParameter(prefix + "appartment");
		zip = request.getParameter(prefix + "zip");
		setForInstalation(Translator.getBoolean(request.getParameter(prefix + "forInstalation")));
		setForOrder(Translator.getBoolean(request.getParameter(prefix + "forOrder")));
		setForReport(Translator.getBoolean(request.getParameter(prefix + "forReport")));
		setForSqueeze(Translator.getBoolean(request.getParameter(prefix + "forSqueeze")));
		setForDealing(Translator.getBoolean(request.getParameter(prefix + "forDealing")));
		setForInvoice(Translator.getBoolean(request.getParameter(prefix + "forInvoice")));
	}

	public ClientFilterVO getClientFilter() {
		return clientFilter;
	}

	public void setClientFilter(ClientFilterVO clientFilter) {
		this.clientFilter = clientFilter;
	}

	public String getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(String selectedClient) {
		this.selectedClient = selectedClient;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getAppartment() {
		return appartment;
	}

	public void setAppartment(String appartment) {
		this.appartment = appartment;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
