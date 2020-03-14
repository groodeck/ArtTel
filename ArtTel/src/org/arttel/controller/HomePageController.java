package org.arttel.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.support.UserContextProvider;
import org.arttel.controller.vo.HomePageVO;
import org.arttel.dao.UserDAO;
import org.arttel.exception.DaoException;
import org.arttel.util.AppSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

	private enum Event {
		LOGIN, LOGOUT, MAIN
	}

	private final Logger log = Logger.getLogger(HomePageController.class);

	@Autowired
	private UserDAO userDao;

	@Autowired
	private UserContextProvider userContextProvider;

	private boolean checkUserPassword( final String user, final String password ) throws DaoException {
		// TODO Auto-generated method stub
		final String hashedPassword = AppSecurity.hashPassword( password );
		return userDao.checkUserPassword( user, hashedPassword );
	}

	private Event getEvent( final HttpServletRequest request, final Event defaultValue) {

		Event event = defaultValue;
		final String eventStr = request.getParameter("event");
		if( eventStr != null ){
			event = Event.valueOf( eventStr.toUpperCase() );
		}
		return event;
	}

	private Map<String,Boolean> getUserPrivileges( final String user ) throws DaoException {
		return userDao.getUserPrivileges( user );
	}

	private void performActionLogin( final UserContext userContext, final HomePageVO homePageVO){

		try {
			final boolean passwordValid = checkUserPassword( homePageVO.getUser(), homePageVO.getPassword() );
			if(passwordValid){
				userContext.setUserLogged(true);
				userContext.setUserName(homePageVO.getUser());
				userContext.setUserPrivileges(getUserPrivileges(homePageVO.getUser()));
			}
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void performActionLogout(final UserContext userContext,
			final HomePageVO homePageVO, final HttpServletRequest request) {

		userContext.setUserLogged(false);
		userContext.setUserName(null);
		request.getSession().invalidate();
	}

	@RequestMapping("/home.app")
	public String process(final HttpServletRequest request,
			final HttpServletResponse response) {

		final UserContext userContext = userContextProvider.getUserContext(request);

		final HomePageVO homePageVO = new HomePageVO();
		homePageVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);

		switch(event){
		case MAIN:
			break;
		case LOGIN:
			performActionLogin(userContext, homePageVO);
			break;
		case LOGOUT:
			performActionLogout(userContext, homePageVO, request);
			break;
		default:
		}
		return "home";
	}

}
