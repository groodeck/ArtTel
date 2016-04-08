package org.arttel.controller.vo;

import org.arttel.view.ComboElement;

import com.google.common.base.Joiner;

public class SellerVO implements ComboElement, InvoiceParticipant {
	
	private String sellerId;
	private String sellerDesc;

	private String nip;
	
	private String city;
	private String street;
	private String house;
	private String appartment;
	private String zip;

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

	@Override
	public String getDesc() {
		return getSellerDesc();
	}

	public String getHouse() {
		return house;
	}

	@Override
	public String getIdn() {
		return getSellerId();
	}

	@Override
	public String getName() {
		return getSellerDesc();
	}

	public String getNip() {
		return nip;
	}

	public String getSellerDesc() {
		return sellerDesc;
	}

	public String getSellerId() {
		return sellerId;
	}

	public String getStreet() {
		return street;
	}

	public String getZip() {
		return zip;
	}

	public void setAppartment(final String appartment) {
		this.appartment = appartment;
	}
	public void setCity(final String city) {
		this.city = city;
	}
	public void setHouse(final String house) {
		this.house = house;
	}
	public void setNip(final String nip) {
		this.nip = nip;
	}
	public void setSellerDesc(final String sellerDesc) {
		this.sellerDesc = sellerDesc;
	}
	public void setSellerId(final String sellerId) {
		this.sellerId = sellerId;
	}
	public void setStreet(final String street) {
		this.street = street;
	}
	public void setZip(final String zip) {
		this.zip = zip;
	}
}
