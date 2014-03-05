
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
import org.arttel.mapper.ControlMapper;
import org.arttel.util.IdnTranslator;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingsController extends BaseController {

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

	private enum Event { 
		MAIN, ADDFUNDS, FUNDSDETAILS, ADDCITY, CITIESDETAILS, ADDCOMPANYCOST, COMPANYCOSTSDETAILS
	}
	
	private static final String SETTINGS = "settings";
	private static final String DEALING_LIST = "dealingList";
	private static final String DEALING_FILTER = "dealingFilter";
	
	@RequestMapping("/settings.app")
	public String process(HttpServletRequest request,
			HttpServletResponse response) {
		
		UserContext userContext = getUserContext(request);

		SettingsVO settingsVO = (SettingsVO) getForm(SettingsVO.class, request);
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
	
	private void performActionCompanyCostDetails(SettingsVO settingsVO,	HttpServletRequest request) {
		
		final List<CompanyCostVO> companyCostList = getCompanyCostList();
		settingsVO.setCompanyCostsList(companyCostList);
		request.setAttribute(SETTINGS, settingsVO);
	}

	private void performActionAddCompanyCost(SettingsVO settingsVO, HttpServletRequest request) {
		settingsVO.populateNewCompanyCost(request);
		if(StringUtils.isNotEmpty(settingsVO.getNewCompanyCost().getCompanyCostDesc())){
			createNewCompanyCost(settingsVO.getNewCompanyCost());
		}
		performActionCompanyCostDetails(settingsVO, request);
		
	}

	private void performActionCityDetails(SettingsVO settingsVO, HttpServletRequest request) {

		final List<CityVO> cityList = getCityList();
		settingsVO.setCityList(cityList);
		request.setAttribute(SETTINGS, settingsVO);
	}
	
	private List<CityVO> getCityList() {
		final List<CityVO> cityList = cityDao.getCityList();
		return cityList;
	}

	private List<CompanyCostVO> getCompanyCostList() {
		return companyCostDao.getCompanyCostList(null);
	}

	private void performActionAddCity(SettingsVO settingsVO, HttpServletRequest request) {
		
		settingsVO.populateNewCity(request);
		if(StringUtils.isNotEmpty(settingsVO.getNewCity().getCityDesc())){
			createNewCity(settingsVO.getNewCity());
		}
		performActionCityDetails(settingsVO, request);
	}
	
	private void createNewCity(CityVO cityVO) {
		final IdnTranslator idnTranslator = new IdnTranslator();
		final String cityIdn = idnTranslator.convertNameToIdn(cityVO.getCityDesc());
		cityVO.setCityIdn(cityIdn);
		cityDao.create(cityVO);
	}

	private void createNewCompanyCost(CompanyCostVO companyCostVO) {
		final IdnTranslator idnTranslator = new IdnTranslator();
		final String costIdn = idnTranslator.convertNameToIdn(companyCostVO.getCompanyCostDesc());
		companyCostVO.setCompanyCostIdn(costIdn);
		companyCostDao.create(companyCostVO);
	}
	
	private void performActionFundsDetails(SettingsVO settingsVO, HttpServletRequest request) {

		final String selectedUser = settingsVO.getNewFunds().getUserName();
		final List<FundsEntryVO> fundsEntryList = fundsEntryDao.getUserFundsEntryList(selectedUser);
		settingsVO.setFundsEntryList(fundsEntryList);
		request.setAttribute(SETTINGS, settingsVO);
	}

	private void performActionAddFunds(SettingsVO settingsVO, HttpServletRequest request) {
		
		try {
			if(StringUtils.isNotEmpty(settingsVO.getNewFunds().getEntryAmount())){
				fundsEntryDao.save(settingsVO.getNewFunds());
				final List<UserBalanceVO> userBalanceList = getUserBalanceList();
				settingsVO.setUserBalanceList(userBalanceList);
			}
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		
		performActionFundsDetails(settingsVO, request);
	}

	private void performActionMain( final UserContext userContext, final SettingsVO settingsVO, final HttpServletRequest request) {
		
		final List<UserBalanceVO> userBalanceList = getUserBalanceList();
		settingsVO.setUserBalanceList(userBalanceList);

		request.setAttribute(SETTINGS, settingsVO);
		request.setAttribute(EVENT, Event.MAIN);
	}

	private List<UserBalanceVO> getUserBalanceList() {
		
		return incomeBalanceCalculator.getUsersBalance();
	}

	protected Event getEvent( final HttpServletRequest request, final Event defaultValue) {
		
		Event event = defaultValue;
		String eventStr = request.getParameter("event");
		if( eventStr != null ){
			event = Event.valueOf( eventStr.toUpperCase() );
		}
		return event;
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap() {
		
		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		try {
			selectsMap.put( "users", userDao.getUserDictionary(false));
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}
}
