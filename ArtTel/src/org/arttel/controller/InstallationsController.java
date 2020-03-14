package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.BasePageVO;
import org.arttel.controller.vo.InstallationVO;
import org.arttel.controller.vo.filter.InstallationFilterVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.UserDAO;
import org.arttel.dictionary.InstallationType;
import org.arttel.dictionary.Status;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.exception.UserNotLoggedException;
import org.arttel.service.InstalationService;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.arttel.ui.TableHeader;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Controller
public class InstallationsController extends BaseController<InstallationVO> {

	private enum Event {
		MAIN, SAVE, EDIT, NEW, DELETE, COPY, SEARCH, BACK, RESET_SOCKET_ORDER, CLOSE, CHANGE_SELECTED_CITY, CLEAR_INSTALATIONS, SORT, CHANGEPAGE
	}

	@Autowired
	private UserDAO userDao;

	@Autowired
	private InstalationService instalationService;

	@Autowired
	private CityDAO cityDao;

	private final Logger log = Logger.getLogger(InstallationsController.class);

	private static final String SELECTED_INSTALATION = "selectedInstalation";
	private static final String INSTALATION_LIST = "instalationList";
	private static final String INSTALATION_FILTER = "instalationFilter";
	private static final String SELECTED_CITY = "selectedCity";
	private static final String UNCLEARED_SOCKET_COUNT = "unclearedSocketCount";
	private static final String UNCLEARED_INSTALATION_COUNT = "unclearedInstalationCount";

	//TODO wyniesc do DB
	final List<String> socketVisibility = Lists.newArrayList("leszek", "jacek", "tomek", "adrian");

	private void fetchSocketAndInstallationCountInfo(
			final HttpServletRequest request) {
		String selectedCity = request.getParameter("selectedCity");
		if(selectedCity == null){
			final List<ComboElement> cityList = getCityDictionary();
			selectedCity = Iterables.getFirst(cityList, null).getIdn();
		}
		final int socketCount = instalationService.getSocketCount(selectedCity);
		final int openInstalationsCount = instalationService.getUnclearedInstalationCount(selectedCity);
		request.setAttribute(SELECTED_CITY, selectedCity);
		request.setAttribute(UNCLEARED_SOCKET_COUNT, socketCount);
		request.setAttribute(UNCLEARED_INSTALATION_COUNT, openInstalationsCount);
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

	private InstallationFilterVO getInstallationFilter(final HttpServletRequest request) {
		InstallationFilterVO filter = (InstallationFilterVO) request.getSession().getAttribute(INSTALATION_FILTER);
		if(filter == null) {
			filter = new InstallationFilterVO();
		}
		return filter;
	}

	@Override
	protected TableHeader getModelDefaultHeader() {
		return InstallationVO.getResultTableHeader();
	}

	@Override
	protected String getResultRecordsListAttrName() {
		return INSTALATION_LIST;
	}

	@Override
	protected String getTableHeaderAttrName() {
		return "installationsTableHeader";
	}

	private boolean isProgressBarVisible(final String userName) {
		return socketVisibility.contains(userName);
	}

	private void performActionChangePage(final UserContext userContext, final HttpServletRequest request) {
		final String newPageNo = request.getParameter(NEW_PAGE_NO);
		updatePage(newPageNo, request);
		searchInstallationsByFilter(userContext, request);
	}

	private void performActionChangeSelectedCity(final UserContext userContext, final HttpServletRequest request) {
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionClearInstalations(final UserContext userContext, final HttpServletRequest request) {
		final String selectedCity = request.getParameter("selectedCity");
		instalationService.clearInstalations(selectedCity);
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionClose(final UserContext userContext, final HttpServletRequest request) {
		final List<Integer> instalationIds = getSelectedRecordsIds(request);
		instalationService.closeInstalations(instalationIds, userContext.getUserName());
		searchInstallationsByFilter(userContext, request);
	}

	private void performActionCopy(final UserContext userContext, final HttpServletRequest request) {
		final List<InstallationVO> selectedRecords = getSelectedRecords(request);
		if(selectedRecords.size() == 1){
			final Integer instalationId = Iterables.getOnlyElement(selectedRecords).getInstallationId();
			final InstallationVO instalationDetails = instalationService.getInstallation(instalationId, userContext.getUserName());
			instalationDetails.setInstallationId(null);
			instalationDetails.getTwoWay1().setInstallationDeviceId(null);
			instalationDetails.getTwoWay2().setInstallationDeviceId(null);
			instalationDetails.getOneWay1().setInstallationDeviceId(null);
			instalationDetails.getOneWay2().setInstallationDeviceId(null);
			instalationDetails.getModem().setInstallationDeviceId(null);
			instalationDetails.setUser(null);
			instalationDetails.applyPermissions(userContext.getUserName());
			request.setAttribute(SELECTED_INSTALATION, instalationDetails);
			request.setAttribute(EVENT, Event.EDIT);
		} else if (selectedRecords.size() > 1){
			request.setAttribute("uiMessage", "Zaznacz dok³adnie jedn¹ instalacjê");
			searchInstallationsByFilter(userContext, request);
		} else {
			searchInstallationsByFilter(userContext, request);
		}
	}

	private void performActionDelete(final UserContext userContext, final HttpServletRequest request) {
		final List<Integer> instalationId = getSelectedRecordsIds(request);
		instalationService.deleteInstalations(instalationId, userContext.getUserName());
		searchInstallationsByFilter(userContext, request);
	}

	private void performActionEdit( final UserContext userContext, final HttpServletRequest request ) {
		final String instalationId = request.getParameter(EVENT_PARAM);
		if(instalationId != null){
			final BasePageVO instalationDetails =
					instalationService.getInstallation(Integer.valueOf(instalationId), userContext.getUserName());
			request.setAttribute(SELECTED_INSTALATION, instalationDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionMain( final HttpServletRequest request) {
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionNew( final UserContext userContext, final HttpServletRequest request ) {

		final InstallationVO instalationDetails = new InstallationVO();
		instalationDetails.setCity("lowicz");
		instalationDetails.applyPermissions(userContext.getUserName());
		request.setAttribute(SELECTED_INSTALATION, instalationDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionResetSocketOrder(final UserContext userContext, final HttpServletRequest request) {
		final String selectedCity = request.getParameter("selectedCity");
		instalationService.resetSocketOrder(selectedCity);
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionSave( final UserContext userContext,
			final InstallationVO instalationVO, final HttpServletRequest request ) {
		instalationVO.populate(request);
		instalationService.save(instalationVO, userContext.getUserName());
		searchInstallationsByFilter(userContext, request);
	}

	private void performActionSearch( final UserContext userContext, final InstallationFilterVO instalationFilterVO, final HttpServletRequest request) {
		instalationFilterVO.populate(request);
		request.getSession().setAttribute(INSTALATION_FILTER, instalationFilterVO);
		searchInstallationsByFilter(userContext, request);
	}

	private void performActionSort(final UserContext userContext, final HttpServletRequest request) {
		final String sortColumn = request.getParameter(SORT_COLUMN);
		updateSortColumn(sortColumn, request);
		searchInstallationsByFilter(userContext, request);
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap() {

		final boolean WITH_EMPTY_OPTION = true;
		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		selectsMap.put( "statusWithEmpty", Status.getComboElementList(true));
		selectsMap.put( "status", Status.getComboElementList(false));
		selectsMap.put( "instalationType", InstallationType.getComboElementList(false));
		selectsMap.put( "instalationTypeWithEmpty", InstallationType.getComboElementList(WITH_EMPTY_OPTION));
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
	public String process(final HttpServletRequest request, final HttpServletResponse response) throws UserNotLoggedException {

		final UserContext userContext = getUserContext(request);
		if(!userContext.isUserLogged()){
			throw new UserNotLoggedException();
		}

		final InstallationVO instalationsVO = (InstallationVO) getForm(InstallationVO.class, request);

		final Event event = getEvent(request, Event.MAIN);
		switch(event){
		case MAIN:
			performActionMain(request);
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
			performActionSearch(userContext, instalationsVO.getInstallationFilter(), request);
			break;
		case BACK:
			searchInstallationsByFilter(userContext, request);
			break;
		case CHANGE_SELECTED_CITY:
			performActionChangeSelectedCity(userContext, request);
			break;
		case RESET_SOCKET_ORDER:
			performActionResetSocketOrder(userContext, request);
			break;
		case CLEAR_INSTALATIONS:
			performActionClearInstalations(userContext, request);
			break;
		case CLOSE:
			performActionClose(userContext, request);
			break;
		case SORT:
			performActionSort(userContext, request);
			break;
		case CHANGEPAGE:
			performActionChangePage(userContext, request);
			break;
		default:

		}
		if(isProgressBarVisible(userContext.getUserName())){
			fetchSocketAndInstallationCountInfo(request);
		}
		request.setAttribute("selectsMap", prepareSelectsMap());
		return "instalations";
	}

	private void searchInstallationsByFilter( final UserContext userContext, final HttpServletRequest request) {
		final PageInfo pageInfo = getCurrentPageInfo(request);
		final InstallationFilterVO instalationFilterVO = getInstallationFilter(request);
		final ResultPage<InstallationVO> instalationList = instalationService.getInstallationList(instalationFilterVO, pageInfo, userContext.getUserName());
		request.getSession().setAttribute(getResultRecordsListAttrName(), instalationList);
		request.setAttribute(EVENT, Event.SEARCH);
	}
}