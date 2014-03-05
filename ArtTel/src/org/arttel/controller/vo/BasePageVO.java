package org.arttel.controller.vo;

import org.arttel.util.WebUtils;

public abstract class BasePageVO {

	private boolean adminLogged;

	public void applyPermissions(final String loggedUser) {
		
		setAdminLogged(WebUtils.isAdmin(loggedUser));
		setEditable(
				isAdminLogged()
				|| loggedUser.equals(getUser())
				|| getUser() == null
		);
	}
	
	protected abstract String getUser();
	protected abstract void setEditable(boolean editable);

	public boolean isAdminLogged() {
		return adminLogged;
	}

	public void setAdminLogged(boolean adminLogged) {
		this.adminLogged = adminLogged;
	}
}
