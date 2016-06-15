
package org.arttel.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.business.IncomeBalanceCalculator;
import org.arttel.controller.vo.DealingVO;
import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.controller.vo.UserBalanceVO;
import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.controller.vo.filter.DealingFilterVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.CompanyCostDAO;
import org.arttel.dao.DealingDAO;
import org.arttel.dao.DictionaryDAO;
import org.arttel.dao.MachineDAO;
import org.arttel.dictionary.DealingType;
import org.arttel.dictionary.DictionaryType;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.ui.TableHeader;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DealingController extends BaseController<DealingVO> {

	private enum Event {
		MAIN, SAVE, EDIT, NEW, DELETE, COPY, SEARCH, BACK, RELOADBALANCE
	}

	@Autowired
	private DealingDAO dealingDao;

	@Autowired
	private CityDAO cityDao;

	@Autowired
	private DictionaryDAO dictionaryDao;

	@Autowired
	private MachineDAO machineDao;

	@Autowired
	private ClientDAO clientDao;

	@Autowired
	private CompanyCostDAO companyCostDao;

	@Autowired
	private IncomeBalanceCalculator incomeBalanceCalculator;

	private final Logger log = Logger.getLogger(DealingController.class);

	private static final String SELECTED_DEALING = "selectedDealing";
	private static final String DEALING_LIST = "dealingList";
	private static final String DEALING_FILTER = "dealingFilter";
	private static final int START_YEAR = 2012;

	private void applyPermissions(final List<DealingVO> dealingList,
			final String loggedUser) {

		for(final DealingVO dealing : dealingList){
			dealing.applyPermissions(loggedUser);
		}
	}

	private String extractDealingYear(final HttpServletRequest request) {
		String dealingYear = request.getParameter("dealingYear" );
		if(StringUtils.isEmpty(dealingYear)){
			dealingYear = String.valueOf(getCurrentYear());
		}
		return dealingYear;
	}

	private List<UserBalanceVO> getCompanyBalance(final HttpServletRequest request, final DealingFilterVO dealingFilterVO) {

		final String dealingYear = extractDealingYear(request);
		final List<UserBalanceVO> companyBalanceList = new ArrayList<UserBalanceVO>();
		final UserBalanceVO companyBalance = incomeBalanceCalculator.getCompanyBalance(dealingYear);
		if(companyBalance != null){
			companyBalanceList.add(companyBalance);
		}
		return companyBalanceList;
	}

	private int getCurrentYear() {
		final Calendar calendar = GregorianCalendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	private List<ComboElement> getDealingYears() {
		final List<ComboElement> resultList = new ArrayList<ComboElement>();
		final int currentYear = getCurrentYear();
		for(int i = START_YEAR; i<=currentYear;i++){
			resultList.add(new SimpleComboElement(String.valueOf(i), String.valueOf(i)));
		}
		return resultList;
	}

	@Override
	protected String getResultRecordsListAttrName() {
		return DEALING_LIST;
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
		return "dealingTableHeader";
	}

	private UserBalanceVO getUserBalance( final String loggedUser) {
		UserBalanceVO userBalance = null;
		try {
			userBalance = incomeBalanceCalculator.getUserBalance(loggedUser);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		return userBalance;
	}

	private List<UserBalanceVO> getUserBalance(final UserContext userContext) {

		final String loggedUser = userContext.getUserName();
		final List<UserBalanceVO> userBalanceList = new ArrayList<UserBalanceVO>();
		final UserBalanceVO userBalance = getUserBalance(loggedUser);
		if(userBalance != null){
			userBalanceList.add(userBalance);
		}
		return userBalanceList;
	}

	private void performActionBackToSearchresults( final UserContext userContext, final HttpServletRequest request) {
		final DealingFilterVO dealingFilterVO = (DealingFilterVO) request.getSession().getAttribute(DEALING_FILTER);
		try{
			final List<DealingVO> dealingList = dealingDao.getDealingList(dealingFilterVO);
			applyPermissions(dealingList, userContext.getUserName());
			request.setAttribute(getResultRecordsListAttrName(), dealingList);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionCopy(final UserContext userContext,
			final HttpServletRequest request) {

		final String dealingId = request.getParameter(EVENT_PARAM);
		try{
			if(dealingId != null){
				final DealingVO dealingDetails = dealingDao.getDealingById(dealingId);
				dealingDetails.setDealingId(null);
				dealingDetails.setUserName(null);
				dealingDetails.applyPermissions(userContext.getUserName());
				request.setAttribute(SELECTED_DEALING, dealingDetails);
			}
		} catch (final DaoException e) {
			log.error("DaoException",e);
		}
		request.setAttribute(EVENT, Event.EDIT);

	}


	private void performActionDelete( final UserContext userContext,
			final HttpServletRequest request ) {

		final String dealingId = request.getParameter(EVENT_PARAM);
		try{
			if(dealingId != null){
				dealingDao.deleteDealingById(dealingId);
			}
		} catch (final DaoException e) {
			log.error("DaoException",e);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionEdit( final UserContext userContext,
			final HttpServletRequest request ) {

		final String dealingId = request.getParameter(EVENT_PARAM);

		try {
			if(dealingId != null){
				final DealingVO dealingDetails = dealingDao.getDealingById(dealingId);
				dealingDetails.applyPermissions(userContext.getUserName());
				request.setAttribute(SELECTED_DEALING, dealingDetails);
			}
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionMain( final UserContext userContext, final HttpServletRequest request) {
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionNew( final UserContext userContext,
			final HttpServletRequest request ) {

		final DealingVO dealingDetails = new DealingVO();
		dealingDetails.applyPermissions(userContext.getUserName());
		request.setAttribute(SELECTED_DEALING, dealingDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionSave( final UserContext userContext,
			final DealingVO dealingVO, final HttpServletRequest request ) {
		try {
			dealingDao.save(dealingVO, userContext.getUserName());
			performActionBackToSearchresults(userContext, request);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void performActionSearch( final UserContext userContext, final DealingFilterVO dealingFilterVO, final HttpServletRequest request) {
		dealingFilterVO.populate(request);
		request.getSession().setAttribute(DEALING_FILTER, dealingFilterVO);
		try{
			final List<DealingVO> dealingList = dealingDao.getDealingList(dealingFilterVO);
			applyPermissions(dealingList, userContext.getUserName());
			request.setAttribute(getResultRecordsListAttrName(), dealingList);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap(final String user) {
		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		selectsMap.put( "dealingType", DealingType.getComboElementList(false));
		try {
			selectsMap.put( "companyCosts", companyCostDao.getCompanyCostDictionary(true, DictionaryPurpose.forDealing));
			final ClientFilterVO clientFilter = new ClientFilterVO(user, DictionaryPurpose.forDealing);
			selectsMap.put( "clients", clientDao.getClientDictionary(true, clientFilter));
			selectsMap.put( "fuel", dictionaryDao.getDictionaryValues(DictionaryType.FUEL, true));
			selectsMap.put( "machines", machineDao.getMachinesComboList(true));
			selectsMap.put( "cityDictionary", cityDao.getCityDictionary(false, DictionaryPurpose.forDealing));
			selectsMap.put( "dealingYears", getDealingYears());
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	@RequestMapping("/dealing.app")
	public String process(final HttpServletRequest request,
			final HttpServletResponse response) {

		final UserContext userContext = getUserContext(request);

		final DealingVO dealingVO = (DealingVO) getForm(DealingVO.class, request);
		dealingVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);

		switch(event){
		case MAIN:
		case RELOADBALANCE:
			performActionMain(userContext, request);
			break;
		case SAVE:
			performActionSave(userContext, dealingVO, request);
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
			performActionSearch(userContext, dealingVO.getDealingFilter(), request);
			break;
		case BACK:
			performActionBackToSearchresults(userContext, request);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap(userContext.getUserName()));
		request.setAttribute("userBalanceList", getUserBalance(userContext));
		request.setAttribute("companyBalanceList", getCompanyBalance(request, dealingVO.getDealingFilter()));
		return "dealing";
	}
}
