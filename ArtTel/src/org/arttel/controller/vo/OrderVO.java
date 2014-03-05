package org.arttel.controller.vo;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.controller.UserContext;
import org.arttel.controller.vo.filter.OrderFilterVO;
import org.arttel.dictionary.InstalationType;
import org.arttel.dictionary.OrderType;
import org.arttel.dictionary.Status;
import org.arttel.util.Translator;
import org.arttel.util.WebUtils;

public class OrderVO extends BasePageVO {
	
	private OrderFilterVO orderFilter = new OrderFilterVO();
	
	private String orderId;
	private Status status;
	private OrderType orderType;
	private Date issueDate;	
	private String phone;
	private String name;
	private String surname;
	private String address;
	private String city;
	private String bundle; //pakiet
	private String serialNumber;
	private Date realizationDate ;	 
	private String solution;
	private String comments;
	private String additionalComments;
	private String problemDescription;
	private String user;
	private boolean editable;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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

	public void populate( final HttpServletRequest request ) {
		orderId = request.getParameter("orderId");
		final String statusStr = request.getParameter("status");
		if(statusStr != null){
			status = Status.getValueByIdn(statusStr);
		}
		final String orderTypeStr = request.getParameter("orderType");
		if(orderTypeStr != null){
			orderType = OrderType.getValueByIdn(orderTypeStr);
		}
		final String issueDateStr = request.getParameter("issueDate");
		issueDate = Translator.parseDate(issueDateStr, null);
		phone = request.getParameter("phone");
		name = request.getParameter("name");
		surname = request.getParameter("surname");
		address = request.getParameter("address");
		city = request.getParameter("city");
		bundle = request.getParameter("bundle");
		serialNumber = request.getParameter("serialNumber");
		final String realizationDateStr = request.getParameter("realizationDate");
		realizationDate = Translator.parseDate(realizationDateStr, null);
		solution = request.getParameter("solution");
		comments = request.getParameter("comments");
		additionalComments = request.getParameter("additionalComments");
		problemDescription = request.getParameter("problemDescription");
		user = request.getParameter("user");
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public OrderFilterVO getOrderFilter() {
		return orderFilter;
	}

	public void setOrderFilter(OrderFilterVO orderFilter) {
		this.orderFilter = orderFilter;
	}

	
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	
	public boolean isClosable(){
		return getStatus() != Status.DONE;
	}
}
