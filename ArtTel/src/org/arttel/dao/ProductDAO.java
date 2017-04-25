package org.arttel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.ProductFilterVO;
import org.arttel.entity.Product;
import org.arttel.util.Translator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductDAO extends BaseDao {


	@PersistenceContext
	private EntityManager em;

	private static final String PRODUCT_QUERY = "FROM Product p WHERE 1=1 ";

	private boolean checkProductAlreadyUsed(final String productId){
		final Long productsCount = (Long)em.createQuery("SELECT count(*) FROM InvoceProducts WHERE productId=" + productId)
				.setParameter(1, productId)
				.getSingleResult();
		if(productsCount > 0){
			return true;
		}
		return false;
	}

	public void deleteProductById(final String productId) {
		if (StringUtils.isNotBlank(productId)
				&& !checkProductAlreadyUsed(productId)) {
			final Product entity = getProductById(productId);
			em.remove(entity);
		}
	}

	public Product getProductById(final String productId) {
		final Product result;
		if (StringUtils.isBlank(productId)) {
			result = null;
		}else {
			result = (Product) em.createQuery("from Product p where p.productId = :productId")
					.setParameter("productId", Translator.parseInteger(productId))
					.getSingleResult();
		}
		return result;
	}

	public List<Product> getProductList(final ProductFilterVO productFilterVO) {
		final String query = prepareQuery(productFilterVO);
		return em.createQuery(query).getResultList();
	}

	private String prepareQuery(final ProductFilterVO productFilterVO) {

		final StringBuilder query = new StringBuilder(PRODUCT_QUERY);

		if(productFilterVO != null){
			final String productName = productFilterVO.getName();
			if(StringUtils.isNotEmpty(productName)){
				query.append(" AND p.productDescription LIKE '%" + productName + "%'");
			}
			query.append(" AND p.user = '" + productFilterVO.getUser() + "'");
		}
		query.append(" ORDER BY p.productId DESC ");

		return query.toString();
	}

	public String save(final Product product) {
		if (product.getProductId() != null) {
			em.merge(product);
		} else {
			em.persist(product);
		}
		return product.getProductId().toString();
	}
}
