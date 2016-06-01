package org.arttel.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.arttel.dictionary.ReportType;

@Entity
@Table(name="User")
public class User {

	@Id
	@GeneratedValue
	@Column(name="UserId")
	private Integer userId;

	@Column(name="UserName")
	private String userName;

	@Column(name="UserPassword")
	private String userPassword;

	@ElementCollection(targetClass=ReportType.class)
	@JoinTable(name = "UserReports", joinColumns = @JoinColumn(name = "userId"))
	@Column(name = "reportType", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<ReportType> userReports;

	public List<ReportType> getUserReports() {
		return userReports;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserReports(final List<ReportType> reports) {
		this.userReports = reports;
	}

	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public void setUserPassword(final String userPassword) {
		this.userPassword = userPassword;
	}

}
