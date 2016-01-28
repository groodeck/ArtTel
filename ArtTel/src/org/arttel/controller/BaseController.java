package org.arttel.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.BasePageVO;
import org.arttel.ui.PageInfo;
import org.arttel.ui.TableHeader;

public abstract class BaseController {

	private final Logger log = Logger.getLogger(BaseController.class);
	public static final String USER_CONTEXT = "userContext";
	public static final String FORM = "form";
	protected static final String RESULT_FILE_LINK = "reportLink";
	protected static final String EVENT = "event";
	protected static final String EVENT_PARAM = "eventParam";
	protected static final String SORT_COLUMN = "sortColumn";
	protected static final String NEW_PAGE_NO = "newPageNo";
	protected static final String IMPORT_FILE_NAME = "importData.xlsx";
	protected static final String DATA_UPLOAD_DIR = "DATA_UPLOAD_DIR";
	protected static final String BASE_DIR = "BASE_DIR";

	protected String jspContextPrefix = "jsp/";

	public static final String TABLE_HEADER = "tableHeader";

	protected PageInfo getCurrentPageInfo(final HttpServletRequest request) {
		final TableHeader tableHeader = getOrCreateTableHeader(request);
		return tableHeader.getPageInfo();
	}

	protected BasePageVO getForm( final Class clazz, final HttpServletRequest request ) {

		BasePageVO formVO = (BasePageVO)request.getSession().getAttribute( FORM );
		try {
			if( formVO == null || !formVO.getClass().equals(clazz)){
				formVO = (BasePageVO)clazz.newInstance();
				request.getSession().setAttribute( FORM, formVO );
			}
		} catch (final InstantiationException e) {
			log.error("InstantiationException", e);
		} catch (final IllegalAccessException e) {
			log.error("IllegalAccessException", e);
		}
		return formVO;
	}

	protected abstract TableHeader getModelDefaultHeader();

	protected PageInfo getNewPageInfo(final HttpServletRequest request) {
		final TableHeader tableHeader = getModelDefaultHeader();
		storeTableHeader(request, tableHeader);
		return tableHeader.getPageInfo();
	}

	public TableHeader getOrCreateTableHeader(final HttpServletRequest request) {
		TableHeader tableHeader = (TableHeader)request.getSession().getAttribute(TABLE_HEADER);
		if(tableHeader == null){
			tableHeader = getModelDefaultHeader();
			storeTableHeader(request, tableHeader);
		}
		return tableHeader;
	}

	protected UserContext getUserContext( final HttpServletRequest request ) {
		UserContext userContext = (UserContext)request.getSession().getAttribute("userContext");
		if( userContext == null ){
			userContext = new UserContext();
			request.getSession().setAttribute( USER_CONTEXT, userContext );
		}
		return userContext;
	}

	private void storeTableHeader(final HttpServletRequest request,
			final TableHeader tableHeader) {
		request.getSession().setAttribute(TABLE_HEADER, tableHeader);
	}

	protected PageInfo updatePage(final String newPageNo, final HttpServletRequest request) {
		final TableHeader tableHeader = getOrCreateTableHeader(request);
		return tableHeader.updatePageNo(request, newPageNo);
	}

	protected PageInfo updateSortColumn(final String sortColumnId, final HttpServletRequest request) {
		final TableHeader tableHeader = getOrCreateTableHeader(request);
		return tableHeader.updateSortColumn(sortColumnId);
	}
}
