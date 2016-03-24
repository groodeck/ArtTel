package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.controller.vo.BasePageVO;
import org.arttel.controller.vo.InstalationVO;
import org.arttel.controller.vo.filter.InstalationFilterVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.InstalationDAO;
import org.arttel.dao.UserDAO;
import org.arttel.dictionary.InstalationType;
import org.arttel.dictionary.Status;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.exception.UserNotLoggedException;
import org.arttel.importer.ImportResult;
import org.arttel.importer.InstalationImporter;
import org.arttel.ui.TableHeader;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Controller
public class InstalationsController extends BaseController {

	private enum Event {
		MAIN, SAVE, EDIT, NEW, DELETE, COPY, SEARCH, BACK, RESET_SOCKET_ORDER, IMPORT_FILE, CLOSE, CHANGE_SELECTED_CITY, CLEAR_INSTALATIONS
	}

	@Autowired
	private UserDAO userDao;

	@Autowired
	private InstalationImporter instalationImporter;

	@Autowired
	private InstalationDAO instalationDao;

	@Autowired
	private CityDAO cityDao;

	private final Logger log = Logger.getLogger(InstalationsController.class);

	private static final String SELECTED_INSTALATION = "selectedInstalation";
	private static final String INSTALATION_LIST = "instalationList";
	private static final String INSTALATION_FILTER = "instalationFilter";
	private static final String SELECTED_CITY = "selectedCity";
	private static final String UNCLEARED_SOCKET_COUNT = "unclearedSocketCount";
	private static final String IMPORT_ERRORS = "importErrors";
	private static final String UNCLEARED_INSTALATION_COUNT = "unclearedInstalationCount";

	//TODO wyniesc do DB
	final List<String> socketVisibility = Lists.newArrayList("leszek", "jacek", "tomek", "adrian");

	private void applyPermissions(final List<InstalationVO> instalationList,
			final String loggedUser) {

		for(final BasePageVO instalation : instalationList){
			instalation.applyPermissions(loggedUser);
		}
	}

	private void closeInstalation(final String instalationId) {
		instalationDao.closeInstalation(instalationId);
	}

	private List<ComboElement> getCityDictionary() {
		List<ComboElement> result;
		try {
			result = cityDao.getCityDictionary(false, DictionaryPurpose.forInstalation);
		} catch (final DaoException e) {
			result = Lists.newArrayList();
		}
		return result;
	}

	protected Event getEvent( final HttpServletRequest request, final Event defaultValue) {

		Event event = defaultValue;
		final String eventStr = request.getParameter("event");
		if( eventStr != null ){
			event = Event.valueOf( eventStr.toUpperCase() );
		}
		return event;
	}

	@Override
	protected TableHeader getModelDefaultHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTableHeaderAttrName() {
		return "instalationsTableHeader";
	}

	private boolean isProgressBarVisible(final String userName) {
		return socketVisibility.contains(userName);
	}

	private void performActionBackToSearchresults( final UserContext userContext, final HttpServletRequest request) {
		final InstalationFilterVO instalationFilterVO = (InstalationFilterVO) request.getSession().getAttribute(INSTALATION_FILTER);
		final List<InstalationVO> instalationList = instalationDao.getInstalationList(instalationFilterVO);
		applyPermissions(instalationList, userContext.getUserName());
		request.setAttribute(INSTALATION_LIST, instalationList);
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionChangeSelectedCity(final UserContext userContext, final HttpServletRequest request) {
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionClearInstalations(final UserContext userContext, final InstalationVO instalationsVO,
			final HttpServletRequest request) {
		final String selectedCity = instalationsVO.getSelectedCity();
		instalationDao.clearInstalations(selectedCity);
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionClose(final UserContext userContext, final HttpServletRequest request) {

		final String instalationId = request.getParameter(EVENT_PARAM);
		if(StringUtils.isNotEmpty(instalationId)){
			closeInstalation(instalationId);
		}
		performActionBackToSearchresults(userContext, request);
	}



	private void performActionCopy(final UserContext userContext,
			final HttpServletRequest request) {

		final String instalationId = request.getParameter(EVENT_PARAM);

		if(instalationId != null){
			final InstalationVO instalationDetails = instalationDao.getInstalationById(instalationId);
			instalationDetails.setInstalationId(null);
			instalationDetails.setUser(null);
			instalationDetails.applyPermissions(userContext.getUserName());
			request.setAttribute(SELECTED_INSTALATION, instalationDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);

	}

	private void performActionDelete( final UserContext userContext,
			final HttpServletRequest request ) {

		final String instalationId = request.getParameter(EVENT_PARAM);

		if(instalationId != null){
			instalationDao.deleteInstalationById(instalationId);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionEdit( final UserContext userContext,
			final HttpServletRequest request ) {

		final String instalationId = request.getParameter(EVENT_PARAM);

		if(instalationId != null){
			final BasePageVO instalationDetails = instalationDao.getInstalationById(instalationId);
			instalationDetails.applyPermissions(userContext.getUserName());
			request.setAttribute(SELECTED_INSTALATION, instalationDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionImportFile(final UserContext userContext, final HttpServletRequest request){

		final String baseDir = request.getServletContext().getInitParameter(BASE_DIR);
		final String uploadDir = request.getServletContext().getInitParameter(DATA_UPLOAD_DIR);
		final String sessionId = request.getSession().getId();
		final String fileDir = baseDir + "/" + uploadDir + "/" + sessionId;
		final String fileName = IMPORT_FILE_NAME;
		final String filePath = fileDir + "/" + fileName;

		final FileExtractor fileExtractor = new FileExtractor();
		fileExtractor.extractFile(request, fileDir, fileName);
		final ImportResult<InstalationVO> importResults = instalationImporter.importInstalations(filePath);
		if(importResults.isDataOK()){
			final List<InstalationVO> instalationList = importResults.getDataList();
			applyPermissions(instalationList, userContext.getUserName());
			request.setAttribute(INSTALATION_LIST, instalationList);
		} else {
			final List<String> errorList = importResults.getErrorList();
			request.setAttribute(IMPORT_ERRORS, errorList);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionMain( final UserContext userContext, final InstalationVO instalationsVO, final HttpServletRequest request) {
		final List<ComboElement> cityList = getCityDictionary();
		final String selectedCity =Iterables.getFirst(cityList, null).getIdn();
		instalationsVO.setSelectedCity(selectedCity);
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionNew( final UserContext userContext,
			final HttpServletRequest request ) {

		final InstalationVO instalationDetails = new InstalationVO();
		instalationDetails.setCity("lowicz");
		instalationDetails.applyPermissions(userContext.getUserName());
		request.setAttribute(SELECTED_INSTALATION, instalationDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionResetSocketOrder(final UserContext userContext, final InstalationVO instalationsVO,
			final HttpServletRequest request) {
		final String selectedCity = instalationsVO.getSelectedCity();
		instalationDao.resetSocketOrder(selectedCity);
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionSave( final UserContext userContext,
			final InstalationVO instalationVO, final HttpServletRequest request ) {
		try {
			instalationDao.save(instalationVO, userContext.getUserName());
			performActionBackToSearchresults(userContext, request);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void performActionSearch( final UserContext userContext, final InstalationFilterVO instalationFilterVO, final HttpServletRequest request) {
		instalationFilterVO.populate(request);
		request.getSession().setAttribute(INSTALATION_FILTER, instalationFilterVO);
		final List<InstalationVO> instalationList = instalationDao.getInstalationList(instalationFilterVO);
		applyPermissions(instalationList, userContext.getUserName());
		request.setAttribute(INSTALATION_LIST, instalationList);
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap() {

		final boolean WITH_EMPTY_OPTION = true;
		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		selectsMap.put( "statusWithEmpty", Status.getComboElementList(true));
		selectsMap.put( "status", Status.getComboElementList(false));
		selectsMap.put( "instalationType", InstalationType.getComboElementList(false));
		selectsMap.put( "instalationTypeWithEmpty", InstalationType.getComboElementList(WITH_EMPTY_OPTION));
		try {
			selectsMap.put( "cityDictionaryWithEmpty", cityDao.getCityDictionary(true, DictionaryPurpose.forInstalation));
			selectsMap.put( "cityDictionary", getCityDictionary());
			selectsMap.put( "users", userDao.getUserDictionary(WITH_EMPTY_OPTION));
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	@RequestMapping("/instalations.app")
	public String process(final HttpServletRequest request,
			final HttpServletResponse response) throws UserNotLoggedException {

		final UserContext userContext = getUserContext(request);
		if(!userContext.isUserLogged()){
			throw new UserNotLoggedException();
		}

		final InstalationVO instalationsVO = (InstalationVO) getForm(InstalationVO.class, request);
		instalationsVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);
		switch(event){
		case MAIN:
			performActionMain(userContext,instalationsVO, request);
			break;
		case SAVE:
			performActionSave(userContext, instalationsVO, request);
			break;
		case EDIT:
			performActionEdit(userContext, request);
			break;
		case NEW:
			performActionNew(userContext, request);
			break;
		case DELETE:
			performActionDelete(userContext, request);
			break;
		case COPY:
			performActionCopy(userContext, request);
			break;
		case SEARCH:
			performActionSearch(userContext, instalationsVO.getInstalationFilter(), request);
			break;
		case BACK:
			performActionBackToSearchresults(userContext, request);
			break;
		case CHANGE_SELECTED_CITY:
			performActionChangeSelectedCity(userContext, request);
			break;
		case RESET_SOCKET_ORDER:
			performActionResetSocketOrder(userContext, instalationsVO, request);
			break;
		case CLEAR_INSTALATIONS:
			performActionClearInstalations(userContext, instalationsVO, request);
			break;
		case IMPORT_FILE:
			performActionImportFile(userContext, request);
			break;
		case CLOSE:
			performActionClose(userContext, request);
			break;
		default:

		}
		if(isProgressBarVisible(userContext.getUserName())){
			final String selectedCity = instalationsVO.getSelectedCity();
			request.setAttribute(SELECTED_CITY, selectedCity);
			final int socketCount = instalationDao.getSocketCount(selectedCity);
			final int openInstalationsCount = instalationDao.getUnclearedInstalationCount(selectedCity);
			request.setAttribute(UNCLEARED_SOCKET_COUNT, socketCount);
			request.setAttribute(UNCLEARED_INSTALATION_COUNT, openInstalationsCount);
		}
		request.setAttribute("selectsMap", prepareSelectsMap());
		return "instalations";
	}
}