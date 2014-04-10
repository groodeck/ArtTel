package org.arttel.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Orders")
public class Order {

	@Id
	@GeneratedValue
	@Column(name="OrderId")
	private Integer orderId;

	@Column(name="Status")
	private String status;
	
	@Column(name="OrderType")
	private String orderType; 
	
	@Column(name="IssueDate")
	private Date issueDate; 
	
	@Column(name="Phone")
	private String phone; 
	
	@Column(name="Name")
	private String name; 
	
	@Column(name="Surname")
	private String surname; 
	
	@Column(name="Address")
	private String address; 
	
	@Column(name="City")
	private String city; 
	
	@Column(name="Bundle")
	private String bundle; 
	
	@Column(name="SerialNumber")
	private String serialNumber;
	
	@Column(name="RealizationDate")
	private Date realizationDate;
	
	@Column(name="Solution")
	private String solution;
	
	@Column(name="Comments")
	private String comments;
	
	@Column(name="AdditionalComments")
	private String additionalComments;
	
	@Column(name="UserId")
	private String userId; 

	@Column(name="ProblemDescription")
	private String problemDescription;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBundle() {
		return bundle;
	}
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Date getRealizationDate() {
		return realizationDate;
	}
	public void setRealizationDate(Date realizationDate) {
		this.realizationDate = realizationDate;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAdditionalComments() {
		return additionalComments;
	}
	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	
}
