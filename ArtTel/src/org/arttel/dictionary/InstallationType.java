package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum InstallationType implements ComboElement{

	MONTAZ("montaz","Monta¿"),
	MODEM("modem", "Modem"),
	SD("sd", "SD"),
	HD_BEZ_MODEMU("hd_bez_modemu", "HD bez modemu"),
	HD("hd", "HD"),
	PVR("pvr", "PVR"),
	TEL_NET("tel_net","Tel+NET"),
	_2_GN("2gn","2 GN"),
	GN_TV("gn_tv","GN_TV"),
	ZMIANA_PAKIETU("zmiana_pakietu","Zmiana pakietu"),
	TERMINAL("terminal","Terminal"),
	PO_KARNYM("po_karnym","Po karnym"),
	PRZENIESIENIE_GN("przeniesienie_gn","Przeniesienie GN"),
	ZWROT("zwrot", "Zwrot"),
	WYMIANA_SPRZETU("wymiana_sprzetu", "Wymiana sprzêtu"),
	INNA("inna","Inna");

	public static List<ComboElement> getComboElementList(final boolean withEmptyOption){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			valuesList.add(new EmptyComboElement());
		}
		for(final InstallationType singleValue : InstallationType.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
	public static InstallationType getValueByDesc(final String param){
		for(final InstallationType singleValue : InstallationType.values()){
			if(singleValue.getDesc().equalsIgnoreCase(param)){
				return singleValue;
			}
		}
		return null;
	}

	public static InstallationType getValueByIdn(final String param){
		for(final InstallationType singleValue : InstallationType.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}

	private String idn;

	private String desc;


	private InstallationType(final String idn, final String desc){
		this.idn = idn;
		this.desc = desc;
	}

	@Override
	public String getDesc() {
		return desc;
	}


	@Override
	public String getIdn() {
		return idn;
	}

}

