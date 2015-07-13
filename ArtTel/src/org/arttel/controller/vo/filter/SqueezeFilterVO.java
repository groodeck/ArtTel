package org.arttel.controller.vo.filter;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public final class SqueezeFilterVO {

	private static final String COMPONENT_NAME = "squeezeFilter";

	private String city;
	private String phrase;

	public String getCity() {
		return city;
	}

	public String getPhrase() {
		return phrase;
	}

	public void populate( final HttpServletRequest request ) {
		city = Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".city" ));
		phrase = Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".phrase" ));
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setPhrase(final String phrase) {
		this.phrase = phrase;
	}
}
