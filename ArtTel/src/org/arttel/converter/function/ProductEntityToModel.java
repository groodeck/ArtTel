package org.arttel.converter.function;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.ProductVO;
import org.arttel.dictionary.UnitType;
import org.arttel.entity.Product;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class ProductEntityToModel implements Function<Product, ProductVO>{

	@Override
	public ProductVO apply(final Product input) {
		final ProductVO productVO = new ProductVO();
		final Integer productId = input.getProductId();
		if(productId != null){
			productVO.setProductId(productId.toString());
		}
		productVO.setProductDescription(input.getProductDescription());
		final BigDecimal netPrice = input.getNetPrice();
		if(netPrice != null){
			productVO.setNetPrice(netPrice.toPlainString());
		}
		productVO.setVatRate(input.getVat());
		final String unitType = input.getUnitType();
		if(StringUtils.isNotBlank(unitType)){
			productVO.setUnitType(UnitType.getValueByIdn(unitType));
		}
		productVO.setComments(input.getComments());
		productVO.setUser(input.getUser());
		productVO.setProductClassification(input.getProductClassification());
		return productVO;
	}
}
