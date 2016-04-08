package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.util.Translator;
import org.arttel.view.ComboElement;

import com.google.common.base.Joiner;

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

	public ClientVO(){
	}

	public ClientVO(final String clientId, final String clientDesc) {
		this.clientId = clientId;
		this.clientDesc = clientDesc;
	}

	@Override
	public String getAddressCity() {
		return Joiner.on(" ").skipNulls().join(getZip(), getCity());
	}
	@Override
	public String getAddressStreet() {
		final String houseAndApptmnt = Joiner.on("/").skipNulls().join(getHouse(), getAppartment());
		return Joiner.on(" ").skipNulls().join(getStreet(), houseAndApptmnt);
	}
	public String getAppartment() {
		return appartment;
	}
	public String getCity() {
		return city;
	}

	public String getClientDesc() {
		return clientDesc;
	}
	public ClientFilterVO getClientFilter() {
		return clientFilter;
	}

	public String getClientId() {
		return clientId;
	}

	@Override
	public String getDesc() {
		return clientDesc;
	}

	public String getHouse() {
		return house;
	}

	@Override
	public String getIdn() {
		return clientId;
	}

	@Override
	public String getName() {
		return clientDesc;
	}

	public String getNip() {
		return nip;
	}

	public String getSelectedClient() {
		return selectedClient;
	}

	public String getStreet() {
		return street;
	}

	public String getZip() {
		return zip;
	}

	public void populate(final HttpServletRequest request, final String prefix) {
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

	public void setAppartment(final String appartment) {
		this.appartment = appartment;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setClientDesc(final String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public void setClientFilter(final ClientFilterVO clientFilter) {
		this.clientFilter = clientFilter;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	public void setHouse(final String house) {
		this.house = house;
	}

	public void setNip(final String nip) {
		this.nip = nip;
	}

	public void setSelectedClient(final String selectedClient) {
		this.selectedClient = selectedClient;
	}
	public void setStreet(final String street) {
		this.street = street;
	}

	public void setZip(final String zip) {
		this.zip = zip;
	}

}
