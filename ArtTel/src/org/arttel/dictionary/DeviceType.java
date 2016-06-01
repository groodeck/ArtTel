package org.arttel.dictionary;

import org.arttel.view.ComboElement;

public enum DeviceType implements ComboElement {

	TWO_WAY_1("TWO_WAY_1", "Dekoder 2 way #1"),
	TWO_WAY_2("TWO_WAY_2", "Dekoder 2 way #2"),
	ONE_WAY("ONE_WAY", "Dekoder 1 way / modu³ ci+"),
	MODEM("MODEM", "Modem NET / Terminal TEL");

	public static DeviceType getValueByIdn(final String param){
		for(final DeviceType singleValue : DeviceType.values()){
			if(singleValue.getIdn().equals(param)){
				return singleValue;
			}
		}
		return null;
	}
	private final String idn;
	private final String desc;

	private DeviceType(final String idn, final String desc){
		this.idn = idn;
		this.desc = desc;
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
