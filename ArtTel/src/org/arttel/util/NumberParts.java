package org.arttel.util;

public class NumberParts {

	private final String wholePart;
	private final String decimalPart;

	public NumberParts(final String wholePart, final String decimalPart) {
		super();
		this.wholePart = wholePart;
		this.decimalPart = decimalPart;
	}

	public String getDecimalPart() {
		return decimalPart;
	}

	public String getWholePart() {
		return wholePart;
	}
}
