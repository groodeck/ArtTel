package org.arttel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.LaborVO;
import org.arttel.dao.LaborDAO;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LaborController extends BaseController {

	@Autowired
	private LaborDAO laborDao;

	private final Logger log = Logger.getLogger(LaborController.class);
	
	private enum Event { 
		MAIN, SAVE
	}
	
	@RequestMapping("/labor.app")
	public String process(HttpServletRequest request,
			HttpServletResponse response) {
		
		UserContext userContext = getUserContext(request);

		LaborVO payoutsVO = (LaborVO) getForm(LaborVO.class, request);
		payoutsVO.populate(request);
		
		final Event event = getEvent(request, Event.MAIN);
		
		switch(event){
		case MAIN:
			performActionMain(userContext, payoutsVO);
			break;
		case SAVE:
			performActionSave(userContext, payoutsVO);
			break;
		default:
		}
		return "labor";
	}

	private void performActionMain( final UserContext userContext, final LaborVO homePageVO) {
		// TODO Auto-generated method stub
		
	}

	private void performActionSave( final UserContext userContext,
			final LaborVO laborVO ) {

		try {
			laborDao.create(laborVO);
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