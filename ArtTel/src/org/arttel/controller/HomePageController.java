package org.arttel.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.HomePageVO;
import org.arttel.dao.UserDAO;
import org.arttel.exception.DaoException;
import org.arttel.util.AppSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController extends BaseController {

	private final Logger log = Logger.getLogger(HomePageController.class);
	
	@Autowired
	private UserDAO userDao;
	
	private enum Event { 
		LOGIN, LOGOUT, MAIN
	}
	
	@RequestMapping("/home.app")
	public String process(HttpServletRequest request,
			HttpServletResponse response) {
		
		UserContext userContext = getUserContext(request);

		//HomePageVO homePageVO = (HomePageVO) getForm(HomePageVO.class, request);
		HomePageVO homePageVO = new HomePageVO();
		homePageVO.populate(request);
		
		final Event event = getEvent(request, Event.MAIN);
		
		switch(event){
		case MAIN:
			performActionMain(userContext, homePageVO);
			break;
		case LOGIN:
			performActionLogin(userContext, homePageVO);
			break;
		case LOGOUT:
			performActionLogout(userContext, homePageVO);
			break;
		default:
		}
		return "home";
	}

	private void performActionMain( final UserContext userContext, final HomePageVO homePageVO) {
		// TODO Auto-generated method stub
		
	}

	private Event getEvent( final HttpServletRequest request, final Event defaultValue) {
		
		Event event = defaultValue;
		String eventStr = request.getParameter("event");
		if( eventStr != null ){
			event = Event.valueOf( eventStr.toUpperCase() );
		}
		return event;
	}

	private void performActionLogin( final UserContext userContext, final HomePageVO homePageVO){
		
		try {
			boolean passwordValid = checkUserPassword( homePageVO.getUser(), homePageVO.getPassword() );
			if(passwordValid){
				userContext.setUserLogged(true);
				userContext.setUserName(homePageVO.getUser());
				userContext.setUserPrivileges(getUserPrivileges(homePageVO.getUser()));
			}
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
	}

	private Map<String,Boolean> getUserPrivileges( final String user ) throws DaoException {
		return userDao.getUserPrivileges( user );
	}

	private boolean checkUserPassword( final String user, final String password ) throws DaoException {
		// TODO Auto-generated method stub
		final String hashedPassword = AppSecurity.hashPassword( password );
		return userDao.checkUserPassword( user, hashedPassword );
	}

	

	private void performActionLogout(UserContext userContext,
			HomePageVO homePageVO) {
		
		userContext.setUserLogged(false);
		userContext.setUserName(null);
	}

}
