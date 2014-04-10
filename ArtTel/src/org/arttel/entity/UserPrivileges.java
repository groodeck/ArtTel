package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserPrivileges")
public class UserPrivileges {

	@Id
	@GeneratedValue
	@Column(name="UserPrivilegesId")
	private Integer userPrivilegesId;
	
	@Column(name="UserId")
	private Integer userId;
	
	@Column(name="PrivilegeIdn")
	private String privilegeIdn;
	
	@Column(name="PrivilegeDesc")
	private String privilegeDesc;

	public Integer getUserPrivilegesId() {
		return userPrivilegesId;
	}

	public void setUserPrivilegesId(Integer userPrivilegesId) {
		this.userPrivilegesId = userPrivilegesId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPrivilegeIdn() {
		return privilegeIdn;
	}

	public void setPrivilegeIdn(String privilegeIdn) {
		this.privilegeIdn = privilegeIdn;
	}

	public String getPrivilegeDesc() {
		return privilegeDesc;
	}

	public void setPrivilegeDesc(String privilegeDesc) {
		this.privilegeDesc = privilegeDesc;
	}
	
}
