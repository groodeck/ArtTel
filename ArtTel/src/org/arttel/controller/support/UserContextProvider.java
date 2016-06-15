package org.arttel.controller.support;

import javax.servlet.http.HttpServletRequest;

import org.arttel.controller.UserContext;
import org.springframework.stereotype.Component;

@Component
public class UserContextProvider {

	public static final String USER_CONTEXT = "userContext";

	public UserContext getUserContext(final HttpServletRequest request) {
		UserContext userContext = (UserContext)request.getSession().getAttribute(USER_CONTEXT);
		if( userContext == null ){
			userContext = new UserContext();
			request.getSession().setAttribute(USER_CONTEXT, userContext );
		}
		return userContext;
	}

}
