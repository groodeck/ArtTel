package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum DocumentType implements ComboElement{

	INVOICE("Faktura"),
	BILL("Rachunek");

	public static List<ComboElement> getComboElementList(final boolean withEmptyOption){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			valuesList.add(new EmptyComboElement());
		}
		for(final DocumentType singleValue : DocumentType.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}

	public static DocumentType getValueByIdn(final String param){
		for(final DocumentType singleValue : DocumentType.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}

	private final String desc;

	private DocumentType(final String desc){
		this.desc = desc;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getIdn() {
		return name();
	}
}

