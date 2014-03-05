package org.arttel.controller.vo.filter;

import javax.servlet.http.HttpServletRequest;

import org.arttel.util.Translator;

public class ProductFilterVO {

	private static final String COMPONENT_NAME = "productFilter";

	private String name;
	private String user;
	
	public void populate( final HttpServletRequest request ) {
		name = Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".name" ));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
