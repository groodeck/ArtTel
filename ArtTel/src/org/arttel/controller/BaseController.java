package org.arttel.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.arttel.controller.support.UserContextProvider;
import org.arttel.controller.vo.BasePageVO;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.arttel.ui.TableHeader;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public abstract class BaseController<VO extends BasePageVO>  {

	private final Logger log = Logger.getLogger(BaseController.class);
	public static final String FORM = "form";
	protected static final String RESULT_FILE_LINK = "reportLink";
	protected static final String EVENT = "event";
	protected static final String EVENT_PARAM = "eventParam";
	protected static final String SORT_COLUMN = "sortColumn";
	protected static final String NEW_PAGE_NO = "newPageNo";
	protected static final String IMPORT_FILE_NAME = "importData.xlsx";
	protected static final String DATA_UPLOAD_DIR = "DATA_UPLOAD_DIR";
	protected static final String BASE_DIR = "BASE_DIR";

	@Autowired
	private UserContextProvider userContextProvider;

	protected String jspContextPrefix = "jsp/";

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
		} catch (final Exception e) {
			log.error("InstantiationException", e);
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
		TableHeader tableHeader = (TableHeader)request.getSession().getAttribute(getTableHeaderAttrName());
		if(tableHeader == null){
			tableHeader = getModelDefaultHeader();
			storeTableHeader(request, tableHeader);
		}
		return tableHeader;
	}

	protected abstract String getResultRecordsListAttrName();

	protected List<String> getSelectedBoxIndexes(final HttpServletRequest request) {
		final Map<String, String[]> parameterMap = request.getParameterMap();
		final List<String> checkedBoxesIndex = FluentIterable.from(parameterMap.keySet())
				.filter(new Predicate<String>(){
					@Override
					public boolean apply(final String input) {
						return input.contains("resultRecordSelected_");
					}})
					.transform(new Function<String, String>(){
						@Override
						public String apply(final String input) {
							return input.replaceAll("resultRecordSelected_", "");
						}})
						.toList();
		return checkedBoxesIndex;
	}

	@SuppressWarnings("unchecked")
	protected List<VO> getSelectedRecords(final HttpServletRequest request) {
		final List<String> selectedIndexes = getSelectedBoxIndexes(request);
		final ResultPage<VO> resultsPage = (ResultPage<VO>)request.getSession().getAttribute(getResultRecordsListAttrName());
		final List<VO> resultRecordsList = resultsPage.getRecords();
		return FluentIterable.from(selectedIndexes)
				.transform(new Function<String, VO>() {
					@Override
					public VO apply(final String input) {
						return resultRecordsList.get(Integer.parseInt(input));
					}}).toList();
	}

	protected List<Integer> getSelectedRecordsIds(final HttpServletRequest request) {
		final List<VO> selectedInvoices = getSelectedRecords(request);
		return FluentIterable.from(selectedInvoices)
				.transform(new Function<VO,Integer>(){
					@Override
					public Integer apply(final VO document) {
						return document.getId();
					}}).toList();
	}

	protected abstract String getTableHeaderAttrName();

	protected UserContext getUserContext(final HttpServletRequest request) {
		return userContextProvider.getUserContext(request);
	}

	private void storeTableHeader(final HttpServletRequest request,
			final TableHeader tableHeader) {
		request.getSession().setAttribute(getTableHeaderAttrName(), tableHeader);
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
