package org.arttel.controller.vo;

import java.math.BigDecimal;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.SqueezeFilterVO;
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
	private boolean editable;
	
	public void populate(HttpServletRequest request) {
		squeezeId = request.getParameter("squeezeId");
		quantity = Translator.parseInt(request.getParameter("quantity"));
		meters = Translator.parseInt(request.getParameter("meters"));
		city = request.getParameter("city");
		clientId = request.getParameter("clientId");
		squeezeDate = Translator.parseDate(request.getParameter("squeezeDate"), null);
		price = Translator.parseDecimalStr(request.getParameter("price"));
		comments = request.getParameter("comments");
		amount = calculateAmount();
	}
	
	private String calculateAmount() {
		final String result;
		if(price == null){
			result = null;
		} else {
			result = multiply(meters, price);
		}
		return result;
	}

	private String multiply(int metersValue, String priceStr) {
		final String result;
		final Double priceValue = Translator.parseDoubleIfNotNull(priceStr);
		if(priceValue == null){
			result = null;
		} else {
			result = Double.toString(metersValue * priceValue.doubleValue());
		}
		return result;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	protected void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getSqueezeId() {
		return squeezeId;
	}

	public void setSqueezeId(String squeezeId) {
		this.squeezeId = squeezeId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getMeters() {
		return meters;
	}

	public void setMeters(int meters) {
		this.meters = meters;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public boolean isEditable() {
		return editable;
	}

	public SqueezeFilterVO getSqueezeFilter() {
		return squeezeFilter;
	}

	public void setSqueezeFilter(SqueezeFilterVO squeezeFilter) {
		this.squeezeFilter = squeezeFilter;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getSqueezeDate() {
		return squeezeDate;
	}

	public void setSqueezeDate(Date squeezeDate) {
		this.squeezeDate = squeezeDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String client) {
		this.clientId = client;
	}

	public void setClientDesc(final String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public String getClientDesc() {
		return clientDesc;
	}

}
