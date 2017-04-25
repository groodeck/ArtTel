package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum VatRate implements ComboElement {
	VAT_ZW("zw", 0.0),
	VAT_0("0", 0.0),
	VAT_5("5", 0.05),
	VAT_8("8", 0.08),
	VAT_23("23", 0.23),
	NIE_PODL_UE("nie podl. UE", 0.0),
	NIE_PODL("nie podl.", 0.0),
	VAT_ROZLICZA_NABYWCA("VAT rozlicza nabywca", 0.0),
	_0_WDT("0% WDT", 0.0),
	_0_EXP("0% Exp", 0.0),
	OBCIAZENIE_ODWROTNE("Obci¹zenie odwrotne", 0.0),
	NP("np.", 0.0),
	NIE_WYSWIETLAJ("nie wyœwietlaj", 0.0);


	public static List<ComboElement> getComboElementList(final boolean withEmptyOption){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			valuesList.add(new EmptyComboElement());
		}
		for(final VatRate singleValue : VatRate.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}

	public static VatRate getValueByIdn(final String param){
		for(final VatRate singleValue : VatRate.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}

	private final String desc;
	private final double value;

	VatRate(final String desc, final double value){
		this.desc = desc;
		this.value = value;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getIdn() {
		return name();
	}

	public double getValue() {
		return value;
	}
}
