package org.arttel.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.PayoutsVO;
import org.arttel.dao.PayoutDAO;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayoutsController extends BaseController {

	@Autowired
	private PayoutDAO payoutDao;

	private final Logger log = Logger.getLogger(PayoutsController.class);
	
	private enum Event { 
		MAIN, SAVE, EDIT, NEW
	}

	private static final String SELECTED_PAYOUT = "selectedPayout";
	private static final String PAYOUT_LIST = "payoutList";
	
	@RequestMapping("/payouts.app")
	public String process(HttpServletRequest request,
			HttpServletResponse response) {
		
		UserContext userContext = getUserContext(request);

		PayoutsVO payoutsVO = (PayoutsVO) getForm(PayoutsVO.class, request);
		payoutsVO.populate(request);
		
		final Event event = getEvent(request, Event.MAIN);
		
		switch(event){
		case MAIN:
			performActionMain(userContext, request);
			break;
		case NEW:
			performActionNew(userContext, request);
			break;
		case EDIT:
			performActionEdit(userContext, request);
			break;
		case SAVE:
			performActionSave(userContext, payoutsVO, request);
			break;
		default:
		}
		return "payouts";
	}

	private void performActionEdit(final UserContext userContext, final HttpServletRequest request) {
		
		final String payoutId = request.getParameter(EVENT_PARAM);
		if(payoutId != null){
			final PayoutsVO payoutDetails = payoutDao.getPayoutById(payoutId);
			request.setAttribute(SELECTED_PAYOUT, payoutDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);
		
	}

	private void performActionNew(final UserContext userContext, final HttpServletRequest request) {
		
		final PayoutsVO payoutDetails = new PayoutsVO();
		request.setAttribute(SELECTED_PAYOUT, payoutDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionMain( final UserContext userContext, final HttpServletRequest request) {
		
		final List<PayoutsVO> payoutList = payoutDao.getPayoutList();
		request.setAttribute(PAYOUT_LIST, payoutList);
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionSave( final UserContext userContext,
			final PayoutsVO payoutsVO, final HttpServletRequest request ) {

		try {
			payoutDao.create(payoutsVO);
			performActionMain(userContext, request);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
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