package org.arttel.controller.vo;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.controller.vo.filter.OrderFilterVO;
import org.arttel.dictionary.OrderType;
import org.arttel.dictionary.Status;
import org.arttel.util.Translator;

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

	public String getAdditionalComments() {
		return additionalComments;
	}

	public String getAddress() {
		return address;
	}

	public String getBundle() {
		return bundle;
	}

	public String getCity() {
		return city;
	}

	public String getComments() {
		return comments;
	}

	@Override
	public Integer getId() {
		return Translator.parseInteger(getOrderId());
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public String getName() {
		return name;
	}

	public OrderFilterVO getOrderFilter() {
		return orderFilter;
	}

	public String getOrderId() {
		return orderId;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public String getPhone() {
		return phone;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public Date getRealizationDate() {
		return realizationDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getSolution() {
		return solution;
	}

	public Status getStatus() {
		return status;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public String getUser() {
		return user;
	}

	public boolean isClosable(){
		return getStatus() != Status.DONE;
	}

	public boolean isEditable() {
		return editable;
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

	public void setAdditionalComments(final String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setBundle(final String bundle) {
		this.bundle = bundle;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	@Override
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	public void setIssueDate(final Date issueDate) {
		this.issueDate = issueDate;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOrderFilter(final OrderFilterVO orderFilter) {
		this.orderFilter = orderFilter;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public void setOrderType(final OrderType orderType) {
		this.orderType = orderType;
	}


	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setProblemDescription(final String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public void setRealizationDate(final Date realizationDate) {
		this.realizationDate = realizationDate;
	}

	public void setSerialNumber(final String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setSolution(final String solution) {
		this.solution = solution;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setUser(final String user) {
		this.user = user;
	}
}
