package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum UnitType implements ComboElement{
	SZT("szt","szt"),
	MB("mb","mb"),
	GODZ("godz","godz"),
	KM("km","km"),
	M("m","m"),
	M2("m2","m2"),
	M3("m3","m3"),
	HA("ha","ha"),
	KG("kg","kg"),
	KPL("kpl","kpl"),
	R_G("r-g","r-g"),
	MG("mg","mg");

	public static List<ComboElement> getComboElementList(final boolean withEmptyOption){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			valuesList.add(new EmptyComboElement());
		}
		for(final UnitType singleValue : UnitType.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
	public static UnitType getValueByIdn(final String param){
		for(final UnitType singleValue : UnitType.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}

	private String idn;

	private String desc;

	private UnitType(final String idn, final String desc){
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

	public void setDesc(final String desc) {
		this.desc = desc;
	}

	public void setIdn(final String idn) {
		this.idn = idn;
	}
}
