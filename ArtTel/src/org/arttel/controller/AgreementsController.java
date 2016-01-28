
package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.AgreementVO;
import org.arttel.controller.vo.filter.AgreementFilterVO;
import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.dao.AgreementDAO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.ClientDAO;
import org.arttel.dictionary.SignPlace;
import org.arttel.dictionary.Status;
import org.arttel.dictionary.YesNo;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.ui.TableHeader;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AgreementsController extends BaseController {

	@Autowired
	AgreementDAO agreementDao;
	
	@Autowired
	private CityDAO cityDao;

	@Autowired
	private ClientDAO clientDao;

	private final Logger log = Logger.getLogger(AgreementsController.class);
	
	private enum Event { 
		MAIN, SAVE, EDIT, NEW, DELETE, COPY, SEARCH, BACK
	}
	
	private static final String SELECTED_AGREEMENT = "selectedAgreement";
	private static final String AGREEMENT_LIST = "agreementList";
	private static final String AGREEMENT_FILTER = "agreementFilter";
	
	@RequestMapping("/agreements.app")
	public String process(final HttpServletRequest request,
			final HttpServletResponse response) {
		
		UserContext userContext = getUserContext(request);

		AgreementVO agreementVO = (AgreementVO) getForm(AgreementVO.class, request);
		agreementVO.populate(request);
		
		final Event event = getEvent(request, Event.MAIN);
		
		switch(event){
		case MAIN:
			performActionMain(userContext, request);
			break;
		case SAVE:
			performActionSave(userContext, agreementVO, request);
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
			performActionSearch(userContext, agreementVO.getAgreementFilter(), request);
			break;
		case BACK:
			performActionBackToSearchresults(userContext, request);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap(userContext.getUserName()));
		return "agreement";
	}

	private void performActionEdit( final UserContext userContext,
			final HttpServletRequest request ) {

		final String agreementId = request.getParameter(EVENT_PARAM);
		
		try {
			if(agreementId != null){
				AgreementVO agreementDetails = agreementDao.getAgreementById(agreementId);
				agreementDetails.applyPermissions(userContext.getUserName());
				request.setAttribute(SELECTED_AGREEMENT, agreementDetails);
			}
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}
	
	private void performActionCopy(final UserContext userContext,
			final HttpServletRequest request) {
		
		final String agreementId = request.getParameter(EVENT_PARAM);
		try{
			if(agreementId != null){
				final AgreementVO agreementDetails = agreementDao.getAgreementById(agreementId);
				agreementDetails.setAgreementId(null);
				agreementDetails.setUser(null);
				agreementDetails.applyPermissions(userContext.getUserName());
				request.setAttribute(SELECTED_AGREEMENT, agreementDetails);
			}
		} catch (DaoException e) {
			log.error("DaoException",e);
		}
		request.setAttribute(EVENT, Event.EDIT);
		
	}

	private void performActionNew( final UserContext userContext,
			final HttpServletRequest request ) {

		final AgreementVO agreementDetails = new AgreementVO();
		agreementDetails.applyPermissions(userContext.getUserName());
		request.setAttribute(SELECTED_AGREEMENT, agreementDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionDelete( final UserContext userContext,
			final HttpServletRequest request ) {

		final String agreementId = request.getParameter(EVENT_PARAM);
		try{
			if(agreementId != null){
				agreementDao.deleteAgreementById(agreementId);
			}
		} catch (DaoException e) {
			log.error("DaoException",e);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionSave( final UserContext userContext,
			final AgreementVO agreementVO, final HttpServletRequest request ) {
		try {
			agreementDao.save(agreementVO, userContext.getUserName());
			performActionBackToSearchresults(userContext, request);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
	}
	
	private void performActionSearch( final UserContext userContext, final AgreementFilterVO agreementFilterVO, final HttpServletRequest request) {
		agreementFilterVO.populate(request);
		request.getSession().setAttribute(AGREEMENT_FILTER, agreementFilterVO);
		try{
			final List<AgreementVO> agreementList = agreementDao.getAgreementList(agreementFilterVO);
			applyPermissions(agreementList, userContext.getUserName());
			request.setAttribute(AGREEMENT_LIST, agreementList);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}
	
	private void applyPermissions(final List<AgreementVO> agreementList,
			final String loggedUser) {
		
		for(final AgreementVO agreement : agreementList){
			agreement.applyPermissions(loggedUser);
		}
	}

	private void performActionBackToSearchresults( final UserContext userContext, final HttpServletRequest request) {
		final AgreementFilterVO agreementFilterVO = (AgreementFilterVO) request.getSession().getAttribute(AGREEMENT_FILTER);
		try{
			final List<AgreementVO> agreementList = agreementDao.getAgreementList(agreementFilterVO);
			applyPermissions(agreementList, userContext.getUserName());
			request.setAttribute(AGREEMENT_LIST, agreementList);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	
	private void performActionMain( final UserContext userContext, final HttpServletRequest request) {
		request.setAttribute(EVENT, Event.MAIN);
	}

	protected Event getEvent( final HttpServletRequest request, final Event defaultValue) {
		
		Event event = defaultValue;
		String eventStr = request.getParameter("event");
		if( eventStr != null ){
			event = Event.valueOf( eventStr.toUpperCase() );
		}
		return event;
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap(final String user) {
		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		selectsMap.put( "status", Status.getComboElementList(false));
		selectsMap.put( "yesNo", YesNo.getComboElementList(false));
		selectsMap.put( "signPlace", SignPlace.getComboElementList(false));
		try {
			final ClientFilterVO clientFilter = new ClientFilterVO(user, DictionaryPurpose.forAgreement);
			selectsMap.put( "clients", clientDao.getClientDictionary(true, clientFilter));
			selectsMap.put( "cityDictionary", cityDao.getCityDictionary(false, DictionaryPurpose.forAgreement));
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	@Override
	protected TableHeader getModelDefaultHeader() {
		// TODO Auto-generated method stub
		return null;
	}
}
