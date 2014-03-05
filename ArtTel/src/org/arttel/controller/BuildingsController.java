package org.arttel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.BuildingVO;
import org.arttel.dao.BuildingDAO;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BuildingsController extends BaseController {

	@Autowired
	private BuildingDAO buildingDao;

	private final Logger log = Logger.getLogger(BuildingsController.class);
	
	private enum Event { 
		MAIN, SAVE
	}
	
	@RequestMapping("/buildings.app")
	public String process(HttpServletRequest request,
			HttpServletResponse response) {
		
		UserContext userContext = getUserContext(request);

		BuildingVO buildingVO = (BuildingVO) getForm(BuildingVO.class, request);
		buildingVO.populate(request);
		
		final Event event = getEvent(request, Event.MAIN);
		
		switch(event){
		case MAIN:
			performActionMain(userContext, buildingVO);
			break;
		case SAVE:
			performActionSave(userContext, buildingVO);
			break;
		default:
		}
		return "buildings";
	}

	private void performActionMain( final UserContext userContext, final BuildingVO disassemblyVO) {
		// TODO Auto-generated method stub
		
	}

	private void performActionSave( final UserContext userContext,
			final BuildingVO buildingVO ) {

		try {
			buildingDao.create(buildingVO);
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