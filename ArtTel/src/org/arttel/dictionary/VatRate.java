package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum VatRate implements ComboElement {
	VAT_ZW("0.0","zw"),
	VAT_0("0.0","0"),
	VAT_5("0.05","5"),
	VAT_8("0.08","8"),
	VAT_23("0.23","23");
	
	private String idn;
	private String desc;
	
	private VatRate(final String idn, final String desc){
		this.idn = idn;
		this.desc = desc;
	}
	
	public static VatRate getValueByIdn(final String param){
		for(VatRate singleValue : VatRate.values()){
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
		for(VatRate singleValue : VatRate.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
}
