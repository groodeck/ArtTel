package org.arttel.dictionary;

import org.arttel.view.ComboElement;

public enum InvoiceStatus implements ComboElement{

	DRAFT("DRAFT", "Szkic", "grey"), 
	PENDING("PENDING", "W realizacji", "FFCC00"), 
	SETTLED("SETTLED", "Rozliczona", "#009900");
	
	private final String idn;
	private final String desc;
	private final String color;
	
	private InvoiceStatus(final String idn, final String desc, final String color){
		this.idn = idn;
		this.desc = desc;
		this.color = color;
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
	
	public static InvoiceStatus getValueByIdn(final String param){
		for(InvoiceStatus singleValue : InvoiceStatus.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}
}
