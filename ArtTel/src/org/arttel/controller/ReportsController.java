package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.support.UserContextProvider;
import org.arttel.controller.vo.ReportParamsVO;
import org.arttel.dao.CityDAO;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.exception.UserNotLoggedException;
import org.arttel.generator.FileGenerator;
import org.arttel.generator.report.ReportsGenerator;
import org.arttel.service.ReportService;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReportsController {
	private enum Event {
		MAIN, GENERATE
	}

	private final Logger log = Logger.getLogger(ReportsController.class);

	@Autowired
	private ReportsGenerator reportsGenerator;

	@Autowired
	private ReportService reportService;

	@Autowired
	private CityDAO cityDao;

	@Autowired
	private FileResponseHandler fileResponse;

	@Autowired
	private UserContextProvider userContextProvider;

	private static final String REPORT_PARAMS = "reportParams";

	protected Event getEvent( final HttpServletRequest request, final Event defaultValue) {

		Event event = defaultValue;
		final String eventStr = request.getParameter("event");
		if( eventStr != null ){
			event = Event.valueOf( eventStr.toUpperCase() );
		}
		return event;
	}

	private ReportParamsVO getForm(final HttpServletRequest request) {
		final Object formVO = request.getSession().getAttribute( BaseController.FORM );
		if(formVO == null || !(formVO instanceof ReportParamsVO)){
			return new ReportParamsVO();
		} else {
			return (ReportParamsVO)formVO;
		}
	}

	private void performActionGenerate(final HttpServletRequest request, final HttpServletResponse response,
			final ReportParamsVO reportParamsVO) {
		try {
			final String reportPath = reportsGenerator.generateReport(reportParamsVO, request.getSession().getId());
			fileResponse.sendToClient(FileGenerator.BASE_DIR + reportPath, response);
		} catch (final Exception e) {
			log.error("Exception", e);
		}
	}

	private void performActionMain( final UserContext userContext, final HttpServletRequest request, final ReportParamsVO reportVO) {

		request.setAttribute(REPORT_PARAMS, reportVO);
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap(final String userName) {

		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		selectsMap.put( "reportType", reportService.getReportDictionary(userName));
		try {
			selectsMap.put( "cityDictionary", cityDao.getCityDictionary(true, DictionaryPurpose.forReport));
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	@RequestMapping("/reports.app")
	public String process(final HttpServletRequest request,
			final HttpServletResponse response) throws UserNotLoggedException {

		final UserContext userContext = userContextProvider.getUserContext(request);
		if(!userContext.isUserLogged()){
			throw new UserNotLoggedException();
		}

		final ReportParamsVO reportsVO = getForm(request);
		reportsVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);

		switch(event){
		case MAIN:
			performActionMain(userContext, request, reportsVO);
			break;
		case GENERATE:
			performActionGenerate(request, response, reportsVO);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap(userContext.getUserName()));
		return "reports";
	}

}
