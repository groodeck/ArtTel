package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum Status implements ComboElement{

	IN_PROGRESS("inProgress","W realizacji","FFCC00"),
	NOT_REALIZED("notRealized","Nie zrealizowane","red"),
	DONE("done","Zrealizowane","#009900"),
	RESIGNATION("resignation", "Rezygnacja", "#00B0F0");
	
	private String idn;
	private String desc;
	private String color;
	
	private Status(final String idn, final String desc, final String color){
		this.idn = idn;
		this.desc = desc;
		this.color = color;
	}
	
	public static Status getValueByIdn(final String param){
		for(Status singleValue : Status.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}
	
	public static Status getValueByDesc(final String desc){
		for(Status singleValue : Status.values()){
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

	public String getColor() {
		return color;
	}
	
	public static List<ComboElement> getComboElementList(boolean withEmptyOption){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			valuesList.add(new EmptyComboElement());
		}
		for(Status singleValue : Status.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
}

