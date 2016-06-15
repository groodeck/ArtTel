package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.ProductFilterVO;
import org.arttel.dictionary.UnitType;
import org.arttel.dictionary.VatRate;
import org.arttel.util.Translator;
import org.arttel.view.ComboElement;

public class ProductVO extends BasePageVO implements ComboElement{

	private final ProductFilterVO productFilter = new ProductFilterVO();
	private String selectedProduct;

	private String productId ;
	private String productDescription;
	private String netPrice;
	private VatRate vatRate;
	private UnitType unitType;
	private String comments;
	private String user;

	public String getComments() {
		return comments;
	}

	@Override
	public String getDesc() {
		return getProductDescription();
	}
	@Override
	public Integer getId() {
		return Translator.parseInteger(getProductId());
	}
	@Override
	public String getIdn() {
		return getProductId();
	}
	public String getNetPrice() {
		return netPrice;
	}
	public String getPriceDecimalPart(){
		return Translator.getNumberParts(netPrice).getDecimalPart();
	}
	public String getPriceWholePart(){
		return Translator.getNumberParts(netPrice).getWholePart();
	}
	public String getProductDescription() {
		return productDescription;
	}
	public ProductFilterVO getProductFilter() {
		return productFilter;
	}
	public String getProductId() {
		return productId;
	}
	public String getSelectedProduct() {
		return selectedProduct;
	}
	public UnitType getUnitType() {
		return unitType;
	}
	@Override
	public String getUser() {
		return user;
	}
	public VatRate getVatRate() {
		return vatRate;
	}

	public void populate(final HttpServletRequest request) {
		productId = request.getParameter("productId");
		productDescription = request.getParameter("productDescription");
		final Double netPriceValue = Translator.parseDoubleIfNotNull(request.getParameter("netPrice"));
		if(netPriceValue != null){
			netPrice = netPriceValue.toString();
		}
		final String vatRateStr= request.getParameter("vatRate");
		if(StringUtils.isNotEmpty(vatRateStr)){
			vatRate	 = VatRate.getValueByIdn(vatRateStr);
		}
		final String unitTypeStr = request.getParameter("unitType");
		if(StringUtils.isNotEmpty(unitTypeStr)){
			unitType = UnitType.getValueByIdn(unitTypeStr);
		}
		comments = request.getParameter("comments");
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	@Override
	protected void setEditable(final boolean editable) {
	}

	public void setNetPrice(final String netPrice) {
		this.netPrice = netPrice;
	}

	public void setProductDescription(final String productDescription) {
		this.productDescription = productDescription;
	}

	public void setProductId(final String productId) {
		this.productId = productId;
	}

	public void setSelectedProduct(final String selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public void setUnitType(final UnitType unitType) {
		this.unitType = unitType;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public void setVatRate(final VatRate vatRate) {
		this.vatRate = vatRate;
	}
}
