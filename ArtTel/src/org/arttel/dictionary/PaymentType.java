package org.arttel.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;

public enum PaymentType implements ComboElement {
	CASH("CASH","Gotówka", 0),
	TRANSFER_7("TRANSFER_7", "Przelew 7 dni", 7),
	TRANSFER_14("TRANSFER_14", "Przelew 14 dni", 14),
	TRANSFER_21("TRANSFER_21", "Przelew 21 dni", 21),
	TRANSFER_30("TRANSFER_30", "Przelew 30 dni", 30);

	private final String idn;
	private final String desc;
	private final int paymentPeriod;
	
	private PaymentType(final String idn, final String desc, final int paymentDays){
		this.idn = idn;
		this.desc = desc;
		this.paymentPeriod = paymentDays;
	}
	
	@Override
	public String getIdn() {
		return idn;
	}

	@Override
	public String getDesc() {
		return desc;
	}
	
	public static PaymentType getValueByIdn(final String param){
		for(PaymentType singleValue : PaymentType.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}
	
	public static List<ComboElement> getComboElementList(boolean withEmptyOption){
		final List<ComboElement> valuesList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			valuesList.add(new EmptyComboElement());
		}
		for(PaymentType singleValue : PaymentType.values()){
			valuesList.add(singleValue);
		}
		return valuesList;
	}
	 
	public boolean isTransfer(){
		return this == TRANSFER_7 
				|| this==TRANSFER_14 
				|| this==TRANSFER_21 
				|| this==TRANSFER_30;
	}

	public int getPaymentPeriod() {
		return paymentPeriod;
	}
}
