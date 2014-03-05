package org.arttel.generator.report;

public enum ReportType {
	
	DECODERS("dekodery");
	
	private String value;
	
	public String getValue() {
		return value;
	}

	private ReportType(final String value){
		this.value = value;
	}
	
	public static ReportType getValueOf(final String valueStr){
		for(final ReportType tmp : ReportType.values()) {
			if(tmp.getValue().equals(valueStr)){
				return tmp;
			}
		}
		return null;
	}
}
