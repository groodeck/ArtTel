package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;

public enum ReportType implements ComboElement{

	INSTALATION("instalation", "Instalacje"),
	ORDER("order", "Zlecenia"),
	AGREEMENT("agreement", "Umowy"),
	DEALING("dealing", "Obrót");

	private String idn;
	private String desc;
	
	private ReportType(final String idn, final String desc){
		this.idn = idn;
		this.desc = desc;
	}
	
	public static ReportType getValueByIdn(final String param){
		for(ReportType singleValue : ReportType.values()){
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

	
	public static List<ComboElement> getComboElementList(){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		for(ReportType singleValue : ReportType.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
	
}

