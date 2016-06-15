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

	public abstract Integer getId();
	protected abstract String getUser();

	public boolean isAdminLogged() {
		return adminLogged;
	}

	public void setAdminLogged(final boolean adminLogged) {
		this.adminLogged = adminLogged;
	}

	protected abstract void setEditable(boolean editable);
}
