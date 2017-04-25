package org.arttel.converter.function;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.ProductVO;
import org.arttel.dictionary.UnitType;
import org.arttel.entity.Product;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class ProductModelToEntity implements Function<ProductVO, Product>{

	@Override
	public Product apply(final ProductVO input) {
		final Product product = new Product();
		final String productId = input.getProductId();
		if(StringUtils.isNotBlank(productId)){
			product.setProductId(Integer.parseInt(productId));
		}
		product.setProductDescription(input.getProductDescription());
		final String netPrice = input.getNetPrice();
		if(StringUtils.isNotBlank(netPrice)){
			product.setNetPrice(new BigDecimal(netPrice));
		}
		product.setVat(input.getVatRate());
		final UnitType unitType = input.getUnitType();
		if(unitType != null){
			product.setUnitType(unitType.getIdn());
		}
		product.setComments(input.getComments());
		product.setUser(input.getUser());
		product.setProductClassification(input.getProductClassification());
		return product;
	}
}
