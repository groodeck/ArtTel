package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum SqueezeStatus implements ComboElement{

	PENDING("PENDING", "W realizacji", "yellow"),
	SETTLED("SETTLED", "Rozliczone", "green"),
	NOT_COMPLETED("NOT_COMPLETED", "Nie rozliczone", "red");

	public static List<ComboElement> getComboElementList(final boolean withEmptyOption){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			valuesList.add(new EmptyComboElement());
		}
		for(final SqueezeStatus singleValue : SqueezeStatus.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
	public static SqueezeStatus getValueByIdn(final String param){
		for(final SqueezeStatus singleValue : SqueezeStatus.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}
	private final String idn;

	private final String desc;

	private final String color;

	private SqueezeStatus(final String idn, final String desc, final String color){
		this.idn = idn;
		this.desc = desc;
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getIdn() {
		return idn;
	}
}
