package org.arttel.controller.vo;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.ProductFilterVO;
import org.arttel.dictionary.UnitType;
import org.arttel.dictionary.VatRate;
import org.arttel.util.Translator;
import org.arttel.view.ComboElement;

public class ProductVO extends BasePageVO implements ComboElement{

	private ProductFilterVO productFilter = new ProductFilterVO();
	private String selectedProduct;
	
	private String productId ;
	private String productDescription;
	private String netPrice;
	private VatRate vatRate;
	private UnitType unitType;
	private String comments;
	
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
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(String netPrice) {
		this.netPrice = netPrice;
	}
	public VatRate getVatRate() {
		return vatRate;
	}
	public void setVatRate(VatRate vatRate) {
		this.vatRate = vatRate;
	}
	public UnitType getUnitType() {
		return unitType;
	}
	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String getIdn() {
		return getProductId();
	}

	@Override
	public String getDesc() {
		return getProductDescription();
	}

	@Override
	protected String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setEditable(boolean editable) {
	}

	public String getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(String selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public ProductFilterVO getProductFilter() {
		return productFilter;
	}
}
