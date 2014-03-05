package org.arttel.controller;

import java.util.Map;

public class UserContext {

	private boolean userLogged;
	private String userName;
	private Map<String,Boolean> userPrivileges;
	
	public Map<String, Boolean> getUserPrivileges() {
		return userPrivileges;
	}
	public void setUserPrivileges(Map<String, Boolean> userPrivileges) {
		this.userPrivileges = userPrivileges;
	}
	public boolean isUserLogged() {
		return userLogged;
	}
	public void setUserLogged(boolean userLogged) {
		this.userLogged = userLogged;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
