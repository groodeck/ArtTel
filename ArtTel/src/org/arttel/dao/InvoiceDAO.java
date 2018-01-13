package org.arttel.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.entity.Invoice;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.arttel.util.Translator;
import org.springframework.stereotype.Repository;

@Repository
@org.springframework.transaction.annotation.Transactional
public class InvoiceDAO extends SortableDataPageFetch {

	@PersistenceContext
	private EntityManager em;

	private static final String LAST_INVOICE_NUMBER_QUERY =
			"select i.documentNumber from Invoice i, Seller s, User u "
					+ "where i.sellerId = s.sellerId "
					+ "and s.user = u.userName "
					+ "and i.createDate >= :createDate "
					+ "and u.userName = :userName "
					+ "ORDER BY i.documentId ";

	public void deleteInvoice(final String invoiceId) {
		final Invoice entity = getInvoiceById(invoiceId);
		em.remove(entity);
	}

	@SuppressWarnings("unchecked")
	public List<String> getDocumentNumbers(final java.util.Date date, final String userName) {

		return em.createQuery(LAST_INVOICE_NUMBER_QUERY)
				.setParameter("createDate", new Date(date.getTime()))
				.setParameter("userName", userName)
				.getResultList();
	}

	public Invoice getInvoiceById(final String invoiceId) {
		final Invoice result;
		if (StringUtils.isBlank(invoiceId)) {
			result = null;
		}else {
			result = (Invoice) em.createQuery("from Invoice i where i.documentId = :documentId")
					.setParameter("documentId", Translator.parseInteger(invoiceId))
					.getSingleResult();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public ResultPage<Invoice> getInvoiceList(final InvoiceFilterVO invoiceFilterVO, final PageInfo pageInfo) {
		final Query query = em.createQuery(prepareQuery(invoiceFilterVO, pageInfo));
		final int allRecordsCount = query.getResultList().size();
		final List<Invoice> resultList = query
				.setMaxResults(MAX_RECORDS_ON_PAGE)
				.setFirstResult(pageInfo.getFirstResult())
				.getResultList();
		return new ResultPage<Invoice>(resultList, pageInfo.getPageNo(), countPages(allRecordsCount));
	}

	private String prepareQuery(final InvoiceFilterVO invoiceFilterVO, final PageInfo pageInfo) {

		final StringBuilder query = new StringBuilder("select object(i) FROM Invoice i, User u WHERE i.user = u.userName ");
		if(invoiceFilterVO != null){
			final String number = invoiceFilterVO.getNumber();
			if(StringUtils.isNotEmpty(number)){
				query.append(" and  i.documentNumber like '%"+ number +"%'");
			}
			final Date createDate = invoiceFilterVO.getCreateDate();
			if(createDate != null){
				query.append(" and  i.createDate = '"+ createDate +"'" );
			}
			query.append(" and  i.user = '"+ invoiceFilterVO.getUser() +"'" );
		}

		final String orderBy = String.format(
				" ORDER BY %s %s ",pageInfo.getSortColumn(), pageInfo.getSortOrder().getClause());
		return query.append(orderBy).toString();
	}

	public String save(final Invoice invoice) {
		if (invoice.getDocumentId() != null && !"".equals(invoice.getDocumentId())) {
			em.merge(invoice);
		} else {
			em.persist(invoice);
		}
		return invoice.getDocumentId().toString();
	}
}
