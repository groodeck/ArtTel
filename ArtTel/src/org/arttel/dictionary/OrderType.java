package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum OrderType implements ComboElement{

	ODLACZENIE("odlaczenie", "Od³¹czenie"),
	PODLACZENIE("podlaczenie", "Pod³¹czenie"),
	ODLACZENIE_KARNE("odlaczenie_karne", "Od³¹czenie karne"),
	PODLACZENIE_PO_KARNYM("podlaczenie_po_karnym", "Pod³¹czenie po Karnym"),
	ZMIANA_PAKIETU("zmiana_pakietu","Zmiana pakietu"),
	USTERKA("usterka","Usterka"),
	AWARIA("awaria","Awaria"),
	ZLECONE_LESZEK("zlecone_leszek","Zlecone Leszek"),
	INNE("inne","Inne");

	private String idn;
	private String desc;
	
	private OrderType(final String idn, final String desc){
		this.idn = idn;
		this.desc = desc;
	}
	
	public static OrderType getValueByIdn(final String param){
		for(OrderType singleValue : OrderType.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}
	
	public static OrderType getValueByDesc(final String param){
		for(OrderType singleValue : OrderType.values()){
			if(singleValue.getDesc().equalsIgnoreCase(param)){
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
		for(OrderType singleValue : OrderType.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
	
}

