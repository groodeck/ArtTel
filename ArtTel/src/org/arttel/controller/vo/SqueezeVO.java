package org.arttel.controller.vo;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.controller.vo.filter.SqueezeFilterVO;
import org.arttel.dictionary.SqueezeStatus;
import org.arttel.util.Translator;

public class SqueezeVO extends BasePageVO{

	private SqueezeFilterVO squeezeFilter = new SqueezeFilterVO();

	private String squeezeId;
	private int quantity;
	private int meters;
	private String city;
	private String clientId;
	private String clientDesc;
	private String amount;
	private Date squeezeDate;
	private String price;
	private String user;
	private String comments;
	private SqueezeStatus status;
	private boolean editable;
	private Integer dealingId;

	private String calculateAmount() {
		final String result;
		if(price == null){
			result = null;
		} else {
			result = multiply(meters, price);
		}
		return result;
	}

	public String getAmount() {
		return amount;
	}

	public String getCity() {
		return city;
	}

	public String getClientDesc() {
		return clientDesc;
	}

	public String getClientId() {
		return clientId;
	}

	public String getComments() {
		return comments;
	}

	public Integer getDealingId() {
		return dealingId;
	}

	public int getMeters() {
		return meters;
	}

	public String getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public Date getSqueezeDate() {
		return squeezeDate;
	}

	public SqueezeFilterVO getSqueezeFilter() {
		return squeezeFilter;
	}

	public String getSqueezeId() {
		return squeezeId;
	}

	public SqueezeStatus getStatus() {
		return status;
	}

	@Override
	public String getUser() {
		return user;
	}

	public boolean isEditable() {
		return editable;
	}

	private String multiply(final int metersValue, final String priceStr) {
		final String result;
		final Double priceValue = Translator.parseDoubleIfNotNull(priceStr);
		if(priceValue == null){
			result = null;
		} else {
			result = Double.toString(metersValue * priceValue.doubleValue());
		}
		return result;
	}

	public void populate(final HttpServletRequest request) {
		squeezeId = request.getParameter("squeezeId");
		quantity = Translator.parseInt(request.getParameter("quantity"));
		meters = Translator.parseInt(request.getParameter("meters"));
		city = request.getParameter("city");
		clientId = request.getParameter("clientId");
		squeezeDate = Translator.parseDate(request.getParameter("squeezeDate"), null);
		price = Translator.parseDecimalStr(request.getParameter("price"));
		comments = request.getParameter("comments");
		amount = calculateAmount();
		final String statusStr = request.getParameter("status");
		if(statusStr != null){
			status = SqueezeStatus.getValueByIdn(statusStr);
		}
		dealingId = Translator.parseInteger(request.getParameter("dealingId"));
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setClientDesc(final String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public void setClientId(final String client) {
		clientId = client;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setDealingId(final Integer dealingId) {
		this.dealingId = dealingId;
	}

	@Override
	protected void setEditable(final boolean editable) {
		this.editable = editable;
	}

	public void setMeters(final int meters) {
		this.meters = meters;
	}

	public void setPrice(final String price) {
		this.price = price;
	}

	public void setQuantity(final int quantity) {
		this.quantity = quantity;
	}

	public void setSqueezeDate(final Date squeezeDate) {
		this.squeezeDate = squeezeDate;
	}

	public void setSqueezeFilter(final SqueezeFilterVO squeezeFilter) {
		this.squeezeFilter = squeezeFilter;
	}

	public void setSqueezeId(final String squeezeId) {
		this.squeezeId = squeezeId;
	}

	public void setStatus(final SqueezeStatus status) {
		this.status = status;
	}

	public void setUser(final String user) {
		this.user = user;
	}

}
