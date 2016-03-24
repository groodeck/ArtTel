
package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.business.IncomeBalanceCalculator;
import org.arttel.controller.vo.CityVO;
import org.arttel.controller.vo.CompanyCostVO;
import org.arttel.controller.vo.FundsEntryVO;
import org.arttel.controller.vo.SettingsVO;
import org.arttel.controller.vo.UserBalanceVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.CompanyCostDAO;
import org.arttel.dao.FundsEntryDAO;
import org.arttel.dao.UserDAO;
import org.arttel.exception.DaoException;
import org.arttel.ui.TableHeader;
import org.arttel.util.IdnTranslator;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingsController extends BaseController {

	private enum Event {
		MAIN, ADDFUNDS, FUNDSDETAILS, ADDCITY, CITIESDETAILS, ADDCOMPANYCOST, COMPANYCOSTSDETAILS
	}

	@Autowired
	private IncomeBalanceCalculator incomeBalanceCalculator;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private CityDAO cityDao;

	@Autowired
	private FundsEntryDAO fundsEntryDao;

	@Autowired
	private CompanyCostDAO companyCostDao;
	private final Logger log = Logger.getLogger(SettingsController.class);

	private static final String SELECTS_MAP = "selectsMap";

	private static final String SETTINGS = "settings";
	private static final String DEALING_LIST = "dealingList";
	private static final String DEALING_FILTER = "dealingFilter";

	private void createNewCity(final CityVO cityVO) {
		final IdnTranslator idnTranslator = new IdnTranslator();
		final String cityIdn = idnTranslator.convertNameToIdn(cityVO.getCityDesc());
		cityVO.setCityIdn(cityIdn);
		cityDao.create(cityVO);
	}

	private void createNewCompanyCost(final CompanyCostVO companyCostVO) {
		final IdnTranslator idnTranslator = new IdnTranslator();
		final String costIdn = idnTranslator.convertNameToIdn(companyCostVO.getCompanyCostDesc());
		companyCostVO.setCompanyCostIdn(costIdn);
		companyCostDao.create(companyCostVO);
	}

	private List<CityVO> getCityList() {
		final List<CityVO> cityList = cityDao.getCityList();
		return cityList;
	}

	private List<CompanyCostVO> getCompanyCostList() {
		return companyCostDao.getCompanyCostList(null);
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
		return "settingsTableHeader";
	}

	private List<UserBalanceVO> getUserBalanceList() {

		return incomeBalanceCalculator.getUsersBalance();
	}

	private void performActionAddCity(final SettingsVO settingsVO, final HttpServletRequest request) {

		settingsVO.populateNewCity(request);
		if(StringUtils.isNotEmpty(settingsVO.getNewCity().getCityDesc())){
			createNewCity(settingsVO.getNewCity());
		}
		performActionCityDetails(settingsVO, request);
	}

	private void performActionAddCompanyCost(final SettingsVO settingsVO, final HttpServletRequest request) {
		settingsVO.populateNewCompanyCost(request);
		if(StringUtils.isNotEmpty(settingsVO.getNewCompanyCost().getCompanyCostDesc())){
			createNewCompanyCost(settingsVO.getNewCompanyCost());
		}
		performActionCompanyCostDetails(settingsVO, request);

	}

	private void performActionAddFunds(final SettingsVO settingsVO, final HttpServletRequest request) {

		try {
			if(StringUtils.isNotEmpty(settingsVO.getNewFunds().getEntryAmount())){
				fundsEntryDao.save(settingsVO.getNewFunds());
				final List<UserBalanceVO> userBalanceList = getUserBalanceList();
				settingsVO.setUserBalanceList(userBalanceList);
			}
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}

		performActionFundsDetails(settingsVO, request);
	}

	private void performActionCityDetails(final SettingsVO settingsVO, final HttpServletRequest request) {

		final List<CityVO> cityList = getCityList();
		settingsVO.setCityList(cityList);
		request.setAttribute(SETTINGS, settingsVO);
	}

	private void performActionCompanyCostDetails(final SettingsVO settingsVO,	final HttpServletRequest request) {

		final List<CompanyCostVO> companyCostList = getCompanyCostList();
		settingsVO.setCompanyCostsList(companyCostList);
		request.setAttribute(SETTINGS, settingsVO);
	}

	private void performActionFundsDetails(final SettingsVO settingsVO, final HttpServletRequest request) {

		final String selectedUser = settingsVO.getNewFunds().getUserName();
		final List<FundsEntryVO> fundsEntryList = fundsEntryDao.getUserFundsEntryList(selectedUser);
		settingsVO.setFundsEntryList(fundsEntryList);
		request.setAttribute(SETTINGS, settingsVO);
	}

	private void performActionMain( final UserContext userContext, final SettingsVO settingsVO, final HttpServletRequest request) {

		final List<UserBalanceVO> userBalanceList = getUserBalanceList();
		settingsVO.setUserBalanceList(userBalanceList);

		request.setAttribute(SETTINGS, settingsVO);
		request.setAttribute(EVENT, Event.MAIN);
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap() {

		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		try {
			selectsMap.put( "users", userDao.getUserDictionary(false));
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	@RequestMapping("/settings.app")
	public String process(final HttpServletRequest request,
			final HttpServletResponse response) {

		final UserContext userContext = getUserContext(request);

		final SettingsVO settingsVO = (SettingsVO) getForm(SettingsVO.class, request);
		settingsVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);

		switch(event){

		case MAIN:
			performActionMain(userContext, settingsVO, request);
			break;
		case ADDFUNDS:
			performActionAddFunds(settingsVO, request);
			break;
		case FUNDSDETAILS:
			performActionFundsDetails(settingsVO, request);
			break;
		case ADDCITY:
			performActionAddCity(settingsVO, request);
			break;
		case CITIESDETAILS:
			performActionCityDetails(settingsVO, request);
			break;
		case ADDCOMPANYCOST:
			performActionAddCompanyCost(settingsVO, request);
			break;
		case COMPANYCOSTSDETAILS:
			performActionCompanyCostDetails(settingsVO, request);
			break;
		default:
		}
		request.setAttribute(SELECTS_MAP, prepareSelectsMap());
		return "settings";
	}
}
