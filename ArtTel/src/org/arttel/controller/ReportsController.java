package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.ReportParamsVO;
import org.arttel.dao.CityDAO;
import org.arttel.dictionary.ReportType;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.exception.UserNotLoggedException;
import org.arttel.generator.report.ReportsGenerator;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReportsController extends BaseController {

	@Autowired
	private ReportsGenerator reportsGenerator;

	@Autowired
	private CityDAO cityDao;
	
	private final Logger log = Logger.getLogger(ReportsController.class);
	private static final String REPORT_PARAMS = "reportParams";
	
	private enum Event { 
		MAIN, GENERATE
	}
	
	@RequestMapping("/reports.app")
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws UserNotLoggedException {
		
		UserContext userContext = getUserContext(request);
		if(!userContext.isUserLogged()){
			throw new UserNotLoggedException();
		}

		ReportParamsVO reportsVO = (ReportParamsVO) getForm(ReportParamsVO.class, request);
		reportsVO.populate(request);
		
		final Event event = getEvent(request, Event.MAIN);
		
		switch(event){
		case MAIN:
			performActionMain(userContext, request, reportsVO);
			break;
		case GENERATE:
			performActionGenerate(userContext, request, reportsVO);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap());
		return "reports";
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap() {
		
		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		selectsMap.put( "reportType", ReportType.getComboElementList());
		try {
			selectsMap.put( "cityDictionary", cityDao.getCityDictionary(true, DictionaryPurpose.forReport));
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	private void performActionGenerate( final UserContext userContext,
			final HttpServletRequest request, final ReportParamsVO reportParamsVO) {
		try {
			final String reportPath = reportsGenerator.generateReport(reportParamsVO, request.getSession().getId());
			request.setAttribute("reportLink", reportPath);
		} catch (Exception e) {
			log.error("Exception", e);
		}
	}

	private void performActionMain( final UserContext userContext, final HttpServletRequest request, final ReportParamsVO reportVO) {

		request.setAttribute(REPORT_PARAMS, reportVO);
		
	}

	protected Event getEvent( final HttpServletRequest request, final Event defaultValue) {
		
		Event event = defaultValue;
		String eventStr = request.getParameter("event");
		if( eventStr != null ){
			event = Event.valueOf( eventStr.toUpperCase() );
		}
		return event;
	}

}
