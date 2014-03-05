package org.arttel.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.BasePageVO;

public abstract class BaseController {

	private final Logger log = Logger.getLogger(BaseController.class);
	public static final String USER_CONTEXT = "userContext";
	public static final String FORM = "form";
	protected static final String RESULT_FILE_LINK = "reportLink";
	protected static final String EVENT = "event";
	protected static final String EVENT_PARAM = "eventParam";
	protected static final String IMPORT_FILE_NAME = "importData.xlsx";
	protected static final String DATA_UPLOAD_DIR = "DATA_UPLOAD_DIR";
	protected static final String BASE_DIR = "BASE_DIR";
	
	protected String jspContextPrefix = "jsp/";
 
	protected UserContext getUserContext( final HttpServletRequest request ) {
		UserContext userContext = (UserContext)request.getSession().getAttribute("userContext");
		if( userContext == null ){
			userContext = new UserContext();
			request.getSession().setAttribute( USER_CONTEXT, userContext );
		}
		return userContext;
	}
	
	protected BasePageVO getForm( final Class clazz, final HttpServletRequest request ) {
		
		BasePageVO formVO = (BasePageVO)request.getSession().getAttribute( FORM );
		try {
			if( formVO == null || !formVO.getClass().equals(clazz)){
				formVO = (BasePageVO)clazz.newInstance();
				request.getSession().setAttribute( FORM, formVO );
			}
		} catch (InstantiationException e) {
			log.error("InstantiationException", e);
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException", e);
		}
		return formVO;
	}
}
