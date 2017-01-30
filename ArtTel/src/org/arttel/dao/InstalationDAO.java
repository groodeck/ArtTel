package org.arttel.dao;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.InstallationVO;
import org.arttel.controller.vo.filter.InstallationFilterVO;
import org.arttel.dictionary.Status;
import org.arttel.entity.Installation;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.arttel.ui.SortOrder;
import org.arttel.ui.SortableColumn;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;

@Repository
@org.springframework.transaction.annotation.Transactional
public class InstalationDAO extends SortableDataPageFetch {

	@PersistenceContext
	private EntityManager em;

	private final Logger log = Logger.getLogger(InstalationDAO.class);

	public void clearInstalations(final String city) {
		final String nextSequenceQuery = "SELECT IFNULL(MAX(instalationClearingSequence)+1,1) FROM instalation";
		final String updateQuery = "UPDATE instalation "
				+ " SET instalationClearingSequence=%s "
				+ " WHERE city ='%s' "
				+ "	AND instalationClearingSequence IS NULL ";

		final BigInteger sequence = (BigInteger) em.createNativeQuery(
				nextSequenceQuery).getSingleResult();
		em.createNativeQuery(String.format(updateQuery, sequence, city))
		.executeUpdate();
	}

	public void closeInstalation(final Integer instalationId) {
		final String query =
				String.format("UPDATE Instalation SET status='%s' WHERE instalationId = %s", Status.DONE.getIdn(), instalationId);
		em.createNativeQuery(query).executeUpdate();
	}

	public void deleteInstalationById(final Integer instalationId) {
		final Installation entity = getInstallationById(instalationId);
		em.remove(entity);
	}

	@SuppressWarnings("unchecked")
	public ResultPage<Installation> getInstalationList(final InstallationFilterVO instalationFilterVO) {

		final SortableColumn sortByAddress = InstallationVO.resultTableHeader.getColumns().get("address");
		sortByAddress.setSortOrder(SortOrder.ASC);
		final String query = prepareQuery(instalationFilterVO, new PageInfo(sortByAddress));
		final List<Installation> resultList = em.createQuery(query).getResultList();
		return new ResultPage<Installation>(resultList, 0, 0);
	}

	@SuppressWarnings("unchecked")
	public ResultPage<Installation> getInstalationList(final InstallationFilterVO instalationFilterVO, final PageInfo pageInfo) {

		final Long allRecordsCount = (Long)em.createQuery(prepareCountQuery(instalationFilterVO)).getSingleResult();
		final Query query = em.createQuery(prepareQuery(instalationFilterVO, pageInfo));
		final List<Installation> resultList = query
				.setMaxResults(MAX_RECORDS_ON_PAGE)
				.setFirstResult(pageInfo.getFirstResult())
				.getResultList();
		return new ResultPage<Installation>(resultList, pageInfo.getPageNo(), countPages(allRecordsCount.intValue()));
	}

	public Installation getInstallationById(final Integer installationId) {
		final Installation result;
		if (installationId == null) {
			result = null;
		} else {
			result = (Installation) em.createQuery("from Installation i where i.instalationId = :installationId")
					.setParameter("installationId", installationId)
					.getSingleResult();
		}
		return result;
	}

	private String getInstallationTypeClause(final List<String> installationTypes) {
		final List<String> results = Lists.newArrayList();
		for(final String installationType : installationTypes){
			results.add(String.format("'%s'", installationType));
		}
		return " and i.installationType in (" + Joiner.on(", ").join(results) + ")";
	}

	public int getSocketCount(final String city) {
		final String query = " SELECT IFNULL(sum(TvSocketCount),0) + IFNULL(sum(NetSocketCount),0) "
				+ " FROM Instalation "
				+ " WHERE city ='%s' "
				+ " AND socketOrderSequence IS NULL ";

		final BigDecimal socketCount = (BigDecimal) em.createNativeQuery(
				String.format(query, city)).getSingleResult();
		return socketCount == null ? 0 : socketCount.intValue();
	}

	public int getUnclearedInstalationCount(final String city) {
		final String query = " SELECT "
				+ " IFNULL(sum(wykonaneInstalacje),0) + "
				+ " IFNULL(sum(dtvCount),0) + "
				+ " IFNULL(sum(atvCount),0) +"
				+ " IFNULL(sum(multiroomCount),0) + "
				+ " IFNULL(sum(netCount),0) + "
				+ " IFNULL(sum(telCount),0) "
				+ " FROM Instalation " + " WHERE city ='%s' "
				+ " AND instalationClearingSequence IS NULL ";

		final BigDecimal installationsCount = (BigDecimal) em
				.createNativeQuery(String.format(query, city))
				.getSingleResult();
		return installationsCount == null ? 0 : installationsCount.intValue();
	}

	private String prepareCountQuery(final InstallationFilterVO instalationFilterVO) {
		return prepareQueryBody("select count(distinct i) from Installation as i", instalationFilterVO).toString();
	}

	private String prepareQuery(final InstallationFilterVO instalationFilterVO, final PageInfo pageInfo) {
		final String orderBy = String.format(" ORDER BY %s %s ", pageInfo.getSortColumn(), pageInfo.getSortOrder().getClause());
		return prepareQueryBody("select distinct i from Installation as i", instalationFilterVO)
				.append(orderBy)
				.toString();
	}

	private StringBuilder prepareQueryBody(final String selectClause, final InstallationFilterVO filter) {
		final StringBuilder query = new StringBuilder(selectClause);
		query.append(" left join i.devices as dvcs");
		query.append(" where i.instalationId is not null ");

		if (filter.getPhrase() != null) {
			query.append(" and  ")
			.append("(")
			.append("i.city like '%" + filter.getPhrase()+ "%'")
			.append(" OR ")
			.append("i.address like '%" + filter.getPhrase() + "%'")
			.append(" OR ")
			.append("i.phone like '%" + filter.getPhrase()	+ "%'").append(")");
		} else {
			query
			.append(filter.getCity() != null ? " and  i.city='" + filter.getCity() + "'" : "")
			.append(filter.getStatus() != null ? " and i.status='"	+ filter.getStatus() + "'"	: "")
			.append(filter.getDateFrom() != null ? " and i.installationDate >= '" + filter.getDateFrom() + "'" : "")
			.append(filter.getDateTo() != null ? " and i.installationDate <= '" + filter.getDateTo() + "'" : "")
			.append(filter.getInstalationType().isEmpty() ? "" : getInstallationTypeClause(filter.getInstalationType()))
			.append(filter.getUser() != null ? " and i.user = '" + filter.getUser() + "'"	: "")
			.append(isNotBlank(filter.getSerial()) ? " and dvcs.serialNumber like '%" + filter.getSerial() + "%'" : "")
			.append(isNotBlank(filter.getMac()) ? " and dvcs.macAddress like '%" + filter.getMac() + "%'" : "");
		}
		return query;
	}

	public boolean resetSocketOrder(final String city) {

		final String nextSequenceQuery = "SELECT IFNULL(MAX(socketOrderSequence)+1,1) FROM instalation";

		final String updateQuery = "UPDATE instalation "
				+ " SET socketOrderSequence=%s "
				+ " WHERE city ='%s' "
				+ "	AND socketOrderSequence IS NULL "
				+ "	AND tvSocketCount > 0 or netSocketCount > 0";

		final BigInteger sequence = (BigInteger) em.createNativeQuery(
				nextSequenceQuery).getSingleResult();
		em.createNativeQuery(String.format(updateQuery, sequence, city))
		.executeUpdate();
		return true;
	}

	public String save(final Installation instalation, final String user) {
		if (instalation.getInstalationId() != null) {
			em.merge(instalation);
		} else {
			em.persist(instalation);
		}
		return instalation.getInstalationId().toString();
	}

}
