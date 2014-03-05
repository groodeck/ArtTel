package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum SignPlace implements ComboElement{

	AB("AB","AB"),
	BOK("BOK","BOK"),
	SF("SF", "SF");
	
	private String idn;
	private String desc;
	
	private SignPlace(final String idn, final String desc){
		this.idn = idn;
		this.desc = desc;
	}
	
	public static SignPlace getValueByIdn(final String param){
		for(SignPlace singleValue : SignPlace.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}
	
	public static SignPlace getValueByDesc(final String desc){
		for(SignPlace singleValue : SignPlace.values()){
			if(singleValue.getDesc().equalsIgnoreCase(desc)){
				return singleValue;
			}
		}
		return null;
	}


	@Override
	public String getIdn() {
		return idn;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	public static List<ComboElement> getComboElementList(boolean withEmptyOption){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			valuesList.add(new EmptyComboElement());
		}
		for(SignPlace singleValue : SignPlace.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
}

