package org.arttel.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.entity.Bill;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.arttel.util.Translator;
import org.springframework.stereotype.Repository;

@Repository
@org.springframework.transaction.annotation.Transactional
public class BillDAO extends SortableDataPageFetch {

	@PersistenceContext
	private EntityManager em;

	private static final String LAST_BILL_NUMBER_QUERY =
			"select b.documentNumber from Bill b, Seller s, User u "
					+ "where b.sellerId = s.sellerId "
					+ "and s.user = u.userName "
					+ "and b.createDate >= :createDate "
					+ "and u.userName = :userName "
					+ "ORDER BY b.documentId ";

	public void deleteBill(final Integer billId) {
		final Bill entity = getBillById(billId.toString());
		em.remove(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Bill> findByDocumentNumber(final String documentNumber, final String userName) {
		return em.createQuery("from Bill b where b.documentNumber = :documentNumber and b.user = :userName")
				.setParameter("documentNumber", documentNumber)
				.setParameter("userName", userName)
				.getResultList();
	}

	public Bill getBillById(final String billId) {
		final Bill result;
		if (StringUtils.isBlank(billId)) {
			result = null;
		}else {
			result = (Bill) em.createQuery("from Bill b where b.documentId = :documentId")
					.setParameter("documentId", Translator.parseInteger(billId))
					.getSingleResult();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public ResultPage<Bill> getBillList(final InvoiceFilterVO documentFilterVO, final PageInfo pageInfo) {
		final Query query = em.createQuery(prepareQuery(documentFilterVO, pageInfo));
		final int allRecordsCount = query.getResultList().size();
		final List<Bill> resultList = query
				.setMaxResults(MAX_RECORDS_ON_PAGE)
				.setFirstResult(pageInfo.getFirstResult())
				.getResultList();
		return new ResultPage<Bill>(resultList, pageInfo.getPageNo(), countPages(allRecordsCount));
	}

	@SuppressWarnings("unchecked")
	public List<String> getDocumentNumbers(final java.util.Date date, final String userName) {
		return em.createQuery(LAST_BILL_NUMBER_QUERY)
				.setParameter("createDate", new Date(date.getTime()))
				.setParameter("userName", userName)
				.getResultList();
	}

	private String prepareQuery(final InvoiceFilterVO documentFilterVO, final PageInfo pageInfo) {

		final StringBuilder query = new StringBuilder("select object(b) FROM Bill b, User u WHERE b.user = u.userName ");
		if(documentFilterVO != null){
			final String number = documentFilterVO.getNumber();
			if(StringUtils.isNotEmpty(number)){
				query.append(" and  b.documentNumber like '%"+ number +"%'");
			}
			final Date createDate = documentFilterVO.getCreateDate();
			if(createDate != null){
				query.append(" and  b.createDate = '"+ createDate +"'" );
			}
			final String nip = documentFilterVO.getNip();
			if(nip != null) {
				query.append(" and .client.nip = '" + nip + "'");
			}
			query.append(" and  b.user = '"+ documentFilterVO.getUser() +"'" );
		}

		final String orderBy = String.format(
				" ORDER BY %s %s ",pageInfo.getSortColumn(), pageInfo.getSortOrder().getClause());
		return query.append(orderBy).toString();
	}

	public String save(final Bill bill) {
		if (bill.getDocumentId() != null && !"".equals(bill.getDocumentId())) {
			em.merge(bill);
		} else {
			em.persist(bill);
		}
		return bill.getDocumentId().toString();
	}

	public void setBillStatus(final String billId, final InvoiceStatus newStatus){
		final Bill bill = getBillById(billId);
		bill.setDocumentStatus(newStatus.getIdn());
	}
}
