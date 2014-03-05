package org.arttel.controller.vo.filter;

import javax.servlet.http.HttpServletRequest;

import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.util.Translator;

public class ClientFilterVO {

	private static final String COMPONENT_NAME = "clientFilter";

	private String name;
	private DictionaryPurpose usedFor;
	private String user;
	
	public ClientFilterVO(final String user, final DictionaryPurpose usedFor){
		this.user = user;
		this.usedFor = usedFor;
	}
	
	public void populate( final HttpServletRequest request ) {
		name = Translator.emptyAsNull(request.getParameter( COMPONENT_NAME + ".name" ));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DictionaryPurpose getUsedFor() {
		return usedFor;
	}

	public void setUsedFor(DictionaryPurpose usedFor) {
		this.usedFor = usedFor;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
