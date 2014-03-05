package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.arttel.controller.vo.CompanyCostVO;
import org.arttel.dictionary.context.ClauseFactory;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class CompanyCostDAO extends BaseDao {

	private static final String COMPANY_COST_LIST_QUERY = 
			"SELECT companyCostsId, companyCostsIdn, companyCostsDesc,forInstalation,forOrder,forReport,forSqueeze,forDealing " +
			"FROM companycosts WHERE true ";
	private final ClauseFactory clauseFactory = new ClauseFactory();
	
	public void create(CompanyCostVO cost) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate("insert into companycosts(companyCostsIdn,companyCostsDesc,forInstalation,forOrder,forReport,forSqueeze,forDealing) values(" +
					"'" + cost.getCompanyCostIdn()+"',"+
					"'" + cost.getCompanyCostDesc()+"',"+
					cost.isForInstalation() + "," +
					cost.isForOrder() + "," +
					cost.isForReport() + "," +
					cost.isForSqueeze() + "," +
					cost.isForDealing() +
					")");
		} catch (SQLException e) {
			log.error("CompanyCostDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		
	}

	public List<CompanyCostVO> getCompanyCostList(DictionaryPurpose usedFor) {
		List<CompanyCostVO> costList = new ArrayList<CompanyCostVO>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = COMPANY_COST_LIST_QUERY + clauseFactory.getWhereClauseFor(usedFor);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				costList.add(extractCost(rs));
			}
		} catch (SQLException e) {
			log.error("CompanyCostDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return costList;
	}
	
	private CompanyCostVO extractCost(ResultSet rs) throws SQLException {
		final String companyCostId = rs.getString(1);
		final String companyCostIdn =rs.getString(2);
		final String companyCostDesc =rs.getString(3);
		boolean forInstalation =rs.getBoolean(4);
		boolean forOrder =rs.getBoolean(5);
		boolean forReport =rs.getBoolean(6);
		boolean forSqueeze =rs.getBoolean(7);
		boolean forDealing =rs.getBoolean(8);
		final CompanyCostVO costVO = new CompanyCostVO(companyCostId, companyCostIdn, companyCostDesc);
		costVO.setForInstalation(forInstalation);
		costVO.setForOrder(forOrder);
		costVO.setForReport(forReport);
		costVO.setForSqueeze(forSqueeze);
		costVO.setForDealing(forDealing);
		return costVO;
	}

	public List<ComboElement> getCompanyCostDictionary(final boolean withEmptyOption, DictionaryPurpose usedFor) {
		final List<ComboElement> companyCostList = Lists.newArrayList();
		if(withEmptyOption){
			companyCostList.add(0, new EmptyComboElement());
		}
		companyCostList.addAll(getCompanyCostList(usedFor));
		return companyCostList;
	}
}
