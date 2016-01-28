package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.business.SqueezeBalanceCalculator;
import org.arttel.controller.vo.SqueezeBalanceVO;
import org.arttel.controller.vo.SqueezeVO;
import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.controller.vo.filter.SqueezeFilterVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.SqueezeDAO;
import org.arttel.dictionary.SqueezeStatus;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.ui.TableHeader;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SqueezesController extends BaseController {

	private enum Event {
		MAIN, SAVE, EDIT, NEW, DELETE, COPY, SEARCH, BACK
	}

	@Autowired
	private CityDAO cityDao;

	@Autowired
	private SqueezeDAO squeezeDao;

	@Autowired
	private ClientDAO clientDao;

	@Autowired
	private SqueezeBalanceCalculator squeezeBalanceCalculator;

	private final Logger log = Logger.getLogger(SqueezesController.class);

	private static final String SELECTED_SQUEEZE = "selectedSqueeze";
	private static final String SQUEEZES_LIST = "squeezeList";
	private static final String SQUEEZE_FILTER = "squeezeFilter";
	private static final String SQUEEZE_BALANCE = "squeezeBalance";

	private void applyPermissions(final List<SqueezeVO> squeezeList,
			final String loggedUser) {

		for(final SqueezeVO squeeze : squeezeList){
			squeeze.applyPermissions(loggedUser);
		}
	}

	protected Event getEvent( final HttpServletRequest request, final Event defaultValue) {

		Event event = defaultValue;
		final String eventStr = request.getParameter("event");
		if(StringUtils.isNotEmpty(eventStr)){
			event = Event.valueOf( eventStr.toUpperCase() );
		}
		return event;
	}

	private SqueezeBalanceVO getSqueezeBalance() {
		return squeezeBalanceCalculator.getSqueezeBalance();
	}

	private void performActionBackToSearchresults( final UserContext userContext, final HttpServletRequest request) {
		final SqueezeFilterVO squeezeFilterVO = (SqueezeFilterVO) request.getSession().getAttribute(SQUEEZE_FILTER);
		try{
			final List<SqueezeVO> squeezeList = squeezeDao.getSqueezeList(squeezeFilterVO);
			applyPermissions(squeezeList, userContext.getUserName());
			request.setAttribute(SQUEEZES_LIST, squeezeList);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(SQUEEZE_BALANCE, getSqueezeBalance());
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionCopy(final UserContext userContext,
			final HttpServletRequest request) {

		final String squeezeId = request.getParameter(EVENT_PARAM);
		try{
			if(squeezeId != null){
				final SqueezeVO squeezeDetails = squeezeDao.getSqueezeById(squeezeId);
				squeezeDetails.setSqueezeId(null);
				squeezeDetails.setUser(null);
				squeezeDetails.applyPermissions(userContext.getUserName());
				request.setAttribute(SELECTED_SQUEEZE, squeezeDetails);
			}
		} catch (final DaoException e) {
			log.error("DaoException",e);
		}
		request.setAttribute(EVENT, Event.EDIT);

	}

	private void performActionDelete( final UserContext userContext,
			final HttpServletRequest request ) {

		final String squeezeId = request.getParameter(EVENT_PARAM);
		try{
			if(squeezeId != null){
				squeezeDao.deleteSqueezeById(squeezeId);
			}
		} catch (final DaoException e) {
			log.error("DaoException",e);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionEdit( final UserContext userContext,
			final HttpServletRequest request ) {

		final String squeezeId = request.getParameter(EVENT_PARAM);

		try {
			if(squeezeId != null){
				final SqueezeVO squeezeDetails = squeezeDao.getSqueezeById(squeezeId);
				squeezeDetails.applyPermissions(userContext.getUserName());
				request.setAttribute(SELECTED_SQUEEZE, squeezeDetails);
			}
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionMain( final UserContext userContext, final HttpServletRequest request) {
		request.setAttribute(SQUEEZE_BALANCE, getSqueezeBalance());
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionNew( final UserContext userContext,
			final HttpServletRequest request ) {

		final SqueezeVO squeezeDetails = new SqueezeVO();
		squeezeDetails.setStatus(SqueezeStatus.PENDING);
		squeezeDetails.applyPermissions(userContext.getUserName());
		request.setAttribute(SELECTED_SQUEEZE, squeezeDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}


	private void performActionSave( final UserContext userContext,
			final SqueezeVO squeezeVO, final HttpServletRequest request ) {
		try {
			squeezeDao.save(squeezeVO, userContext.getUserName());
			performActionBackToSearchresults(userContext, request);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void performActionSearch( final UserContext userContext, final SqueezeFilterVO squeezeFilterVO, final HttpServletRequest request) {
		squeezeFilterVO.populate(request);
		request.getSession().setAttribute(SQUEEZE_FILTER, squeezeFilterVO);
		try{
			final List<SqueezeVO> squeezeList = squeezeDao.getSqueezeList(squeezeFilterVO);
			applyPermissions(squeezeList, userContext.getUserName());
			request.setAttribute(SQUEEZES_LIST, squeezeList);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(SQUEEZE_BALANCE, getSqueezeBalance());
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap(final String user) {
		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		try {
			final ClientFilterVO clientFilter = new ClientFilterVO(user, DictionaryPurpose.forSqueeze);
			selectsMap.put( "status", SqueezeStatus.getComboElementList(false));
			selectsMap.put( "cityDictionary", cityDao.getCityDictionary(true, DictionaryPurpose.forSqueeze));
			selectsMap.put( "clientDictionary", clientDao.getClientDictionary(true, clientFilter));
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	@RequestMapping("/squeezes.app")
	public String process(final HttpServletRequest request, final HttpServletResponse response) {

		final UserContext userContext = getUserContext(request);

		final SqueezeVO squeezeVO = (SqueezeVO) getForm(SqueezeVO.class, request);
		squeezeVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);

		switch(event){
		case MAIN:
			performActionMain(userContext, request);
			break;
		case SAVE:
			performActionSave(userContext, squeezeVO, request);
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
			performActionSearch(userContext, squeezeVO.getSqueezeFilter(), request);
			break;
		case BACK:
			performActionBackToSearchresults(userContext, request);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap(userContext.getUserName()));
		return "squeezes";
	}

	@Override
	protected TableHeader getModelDefaultHeader() {
		// TODO Auto-generated method stub
		return null;
	}

}
