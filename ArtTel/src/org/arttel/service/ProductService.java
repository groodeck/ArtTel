package org.arttel.service;

import java.util.List;

import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.filter.ProductFilterVO;
import org.arttel.converter.function.ProductEntityToModel;
import org.arttel.converter.function.ProductModelToEntity;
import org.arttel.dao.ProductDAO;
import org.arttel.entity.Product;
import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

@Component
public class ProductService {

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private ProductEntityToModel entityToModel;

	@Autowired
	private ProductModelToEntity modelToEntity;

	public void deleteProductById(final String productId) {
		productDao.deleteProductById(productId);
	}

	public ProductVO getProductById(final String productId) {
		final Product entity = productDao.getProductById(productId);
		return entityToModel.apply(entity);
	}

	public List<? extends ComboElement> getProductDictionary(final boolean withEmptyOption, final String user) {
		final List<ComboElement> productList = Lists.newArrayList();
		if(withEmptyOption){
			productList.add(0, new EmptyComboElement());
		}
		final ProductFilterVO filter = new ProductFilterVO();
		filter.setUser(user);
		productList.addAll(getProductList(filter));
		return productList;
	}

	public List<ProductVO> getProductList(final ProductFilterVO productFilterVO) {
		final List<Product> productList = productDao.getProductList(productFilterVO);
		return FluentIterable.from(productList).transform(entityToModel).toList();
	}

	public void save(final ProductVO productVO, final String userName) {
		productVO.setUser(userName);
		productDao.save(modelToEntity.apply(productVO));
	}

}
