package org.arttel.controller.vo.filter;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public final class SqueezeFilterVO {

	private static final String COMPONENT_NAME = "squeezeFilter";

	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public void populate( final HttpServletRequest request ) {
		city = Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".city" ));
	}
}
