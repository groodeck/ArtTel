package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum UnitType implements ComboElement{
	SZT("szt","szt"),
	MB("mb","mb");
	
	private String idn;
	private String desc;
	
	private UnitType(final String idn, final String desc){
		this.idn = idn;
		this.desc = desc;
	}
	
	public static UnitType getValueByIdn(final String param){
		for(UnitType singleValue : UnitType.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}

	public String getIdn() {
		return idn;
	}

	public void setIdn(String idn) {
		this.idn = idn;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static List<ComboElement> getComboElementList(boolean withEmptyOption){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			valuesList.add(new EmptyComboElement());
		}
		for(UnitType singleValue : UnitType.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
}
