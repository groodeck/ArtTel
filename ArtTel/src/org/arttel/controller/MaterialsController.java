package org.arttel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.MaterialsVO;
import org.arttel.dao.MaterialDAO;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MaterialsController extends BaseController {

	@Autowired
	private MaterialDAO materialDao;

	private final Logger log = Logger.getLogger(MaterialsController.class);
	
	private enum Event { 
		MAIN, SAVE
	}
	
	@RequestMapping("/materials.app")
	public String process(HttpServletRequest request,
			HttpServletResponse response) {
		
		UserContext userContext = getUserContext(request);

		MaterialsVO materialsVO = (MaterialsVO) getForm(MaterialsVO.class, request);
		materialsVO.populate(request);
		
		final Event event = getEvent(request, Event.MAIN);
		
		switch(event){
		case MAIN:
			performActionMain(userContext, materialsVO);
			break;
		case SAVE:
			performActionSave(userContext, materialsVO);
			break;
		default:
		}
		return "materials";
	}

	private void performActionMain( final UserContext userContext, final MaterialsVO homePageVO) {
		// TODO Auto-generated method stub
		
	}

	private void performActionSave( final UserContext userContext,
			final MaterialsVO materialsVO ) {

		try {
			materialDao.create(materialsVO);
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