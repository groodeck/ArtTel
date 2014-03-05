package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum DealingType implements ComboElement{

	COSTS("costs","Koszty","red"),
	INCOME("income","Przychód","#009900");
	
	private final String idn;
	private final String desc;
	private final String color;
	
	private DealingType(final String idn, final String desc, final String color){
		this.idn = idn;
		this.desc = desc;
		this.color = color;
	}
	
	public static DealingType getValueByIdn(final String param){
		for(DealingType singleValue : DealingType.values()){
			if(singleValue.getIdn().equals(param)){
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
		for(DealingType singleValue : DealingType.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}

	public String getColor() {
		return color;
	}
}

