package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.ClientVO;
import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.dictionary.context.ClauseFactory;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.entity.Client;
import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class ClientDAO extends BaseDao {

	@PersistenceContext
	private EntityManager em;

	private static final String CLIENT_QUERY =
			"SELECT clientId, clientDesc,forInstalation,forOrder,forReport,forSqueeze,forDealing,forInvoice, nip, city, street, house, appartment, zip " +
					"FROM Client " +
					"WHERE true ";

	private final ClauseFactory clauseFactory = new ClauseFactory();

	private boolean checkClientAlreadyUsed(final String clientId) throws SQLException {
		final Statement stmt = getConnection().createStatement();
		final ResultSet rs = stmt.executeQuery(
				"SELECT count(*) FROM Invoice WHERE clientId=" + clientId);
		if(rs.next()){
			final long clientInvoicesCount = rs.getLong(1);
			if(clientInvoicesCount > 0){
				return true;
			}
		}
		return false;
	}

	public void create(final ClientVO client, final String userName) {
		Statement stmt = null;
		final ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate("insert into Client (clientDesc,forInstalation,forOrder,forReport,forSqueeze,forDealing,forInvoice, nip, city, street, house, appartment, zip, user) "
					+ "values(" +
					"'" + client.getClientDesc()+"',"+
					client.isForInstalation() + "," +
					client.isForOrder() + "," +
					client.isForReport() + "," +
					client.isForSqueeze() + "," +
					client.isForDealing() + "," +
					client.isForInvoice() + ",'" +
					client.getNip() + "','" +
					client.getCity() + "','" +
					client.getStreet() + "','" +
					client.getHouse() + "','" +
					client.getAppartment() + "','" +
					client.getZip() + "','" +
					userName +
					"')");
		} catch (final SQLException e) {
			log.error("ClientDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
	}

	public void deleteClientById(final String clientId) {
		if (clientId != null && !"".equals(clientId)) {
			final String query = String.format("DELETE FROM Client WHERE clientId = %s", clientId);
			Statement stmt = null;
			try {
				if(!checkClientAlreadyUsed(clientId)){
					stmt = getConnection().createStatement();
					stmt.executeUpdate(query);
				}
			} catch (final SQLException e) {
				log.error("ClientDAO exception: ", e);
			} finally {
				disconnect(stmt, null);
			}
		}
	}

	private ClientVO extractClient(final ResultSet rs) throws SQLException {
		final ClientVO clientVO = new ClientVO(rs.getString(1), rs.getString(2));
		clientVO.setForInstalation(rs.getBoolean(3));
		clientVO.setForOrder(rs.getBoolean(4));
		clientVO.setForReport(rs.getBoolean(5));
		clientVO.setForSqueeze(rs.getBoolean(6));
		clientVO.setForDealing(rs.getBoolean(7));
		clientVO.setForInvoice(rs.getBoolean(8));
		clientVO.setNip(rs.getString(9));
		clientVO.setCity(rs.getString(10));
		clientVO.setStreet(rs.getString(11));
		clientVO.setHouse(rs.getString(12));
		clientVO.setAppartment(rs.getString(13));
		clientVO.setZip(rs.getString(14));
		return clientVO;
	}

	public Client getClientById(final String clientId) {
		return (Client) em.createQuery("from Client where clientId = :clientId")
				.setParameter("clientId", Integer.parseInt(clientId))
				.getSingleResult();
	}

	public List<ComboElement> getClientDictionary(final boolean withEmptyOption, final ClientFilterVO clientFilter) {
		final List<ComboElement> clientList = Lists.newArrayList();
		if(withEmptyOption){
			clientList.add(0, new EmptyComboElement());
		}
		clientList.addAll(getClientList(clientFilter));
		return clientList;
	}

	public List<ClientVO> getClientList(final ClientFilterVO clientFilterVO) {
		final List<ClientVO> clientList = new ArrayList<ClientVO>();
		Statement stmt = null;
		ResultSet rs = null;
		final String query = prepareQuery(clientFilterVO);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				clientList.add(extractClient(rs));
			}
		} catch (final SQLException e) {
			log.error("ClientDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return clientList;
	}

	public ClientVO getClientVoById(final String clientId) {
		//TODO wywal to i przenis konwersje do ClientService
		ClientVO result = null;
		if (StringUtils.isNotEmpty(clientId)) {
			final String query = CLIENT_QUERY.concat(
					String.format(" AND clientId = %s", clientId));
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					result = extractClient(rs);
				}
			} catch (final SQLException e) {
				log.error("ProductDAO SQLException", e);
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	private String prepareQuery(final ClientFilterVO clientFilterVO) {

		final StringBuilder query = new StringBuilder(CLIENT_QUERY);

		if(clientFilterVO != null){
			final String clientName = clientFilterVO.getName();
			if(StringUtils.isNotEmpty(clientName)){
				query.append(" AND clientDesc LIKE '%" + clientName + "%' ");
			}
			final DictionaryPurpose usedFor = clientFilterVO.getUsedFor();
			if(usedFor != null){
				query.append(clauseFactory.getWhereClauseFor(usedFor));
			}
			final String user = clientFilterVO.getUser();
			if(user != null){
				query.append(" AND user = '" + user + "' ");
			}
		}
		query.append(" ORDER BY clientId DESC ");

		return query.toString();
	}

	public void save(final ClientVO clientVO, final String userName) {
		if(StringUtils.isNotEmpty(clientVO.getClientId())){
			update(clientVO, userName);
		} else {
			create(clientVO, userName);
		}
	}

	private void update(final ClientVO clientVO, final String userName) {
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			final String query =
					"UPDATE Client SET "
							+ "clientDesc = '" + clientVO.getClientDesc()
							+ "', forInstalation = " + clientVO.isForInstalation()
							+ ", forOrder = " + clientVO.isForOrder()
							+ ", forReport = " + clientVO.isForReport()
							+ ", forSqueeze = " + clientVO.isForSqueeze()
							+ ", forDealing = " + clientVO.isForDealing()
							+ ", forInvoice = " + clientVO.isForInvoice()
							+ ", nip = '" + clientVO.getNip()
							+ "', city = '" + clientVO.getCity()
							+ "', street = '" + clientVO.getStreet()
							+ "', house = '" + clientVO.getHouse()
							+ "', appartment = '" + clientVO.getAppartment()
							+ "', zip = '" + clientVO.getZip()
							+ "' WHERE clientId = " + clientVO.getClientId();
			stmt.executeUpdate(query);

		} catch (final SQLException e) {
			log.error("ClientDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
	}

}
