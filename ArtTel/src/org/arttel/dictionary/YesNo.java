package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum YesNo implements ComboElement{

	YES("1","Tak"),
	NO("0","Nie");
	
	private String idn;
	private String desc;
	
	private YesNo(final String idn, final String desc){
		this.idn = idn;
		this.desc = desc;
	}
	
	public static YesNo getValueByIdn(final String param){
		for(YesNo singleValue : YesNo.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}
	
	public static YesNo getValueByDesc(final String desc){
		for(YesNo singleValue : YesNo.values()){
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
		for(YesNo singleValue : YesNo.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
}

