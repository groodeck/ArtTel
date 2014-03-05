package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.arttel.controller.vo.CityVO;
import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.dictionary.context.ClauseFactory;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;
import org.springframework.stereotype.Component;

@Component
public class CityDAO extends BaseDao {

	private final ClauseFactory clauseFactory = new ClauseFactory();
	
	public List<ComboElement> getCityDictionary(boolean withEmptyOption, DictionaryPurpose useFor) throws DaoException {

		String query = "SELECT cityIdn, cityDesc FROM City WHERE true " + clauseFactory.getWhereClauseFor(useFor);

		List<ComboElement> cityList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			cityList.add(new EmptyComboElement());
		}
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				cityList.add(new SimpleComboElement(rs.getString(1),rs.getString(2)));
			}
		} catch (SQLException e) {
			throw new DaoException("CityDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return cityList;
	}
	
	public List<CityVO> getCityList() {

		List<CityVO> cityList = new ArrayList<CityVO>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery("select cityId, cityIdn, cityDesc, forInstalation,forOrder,forReport,forSqueeze,forDealing from City");
			while(rs.next()){
				cityList.add(extractCity(rs));
			}
		} catch (SQLException e) {
			log.error("CityDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return cityList;
	}

	private CityVO extractCity(ResultSet rs) throws SQLException {
		final String cityId = rs.getString(1);
		final String cityIdn =rs.getString(2);
		final String cityDesc =rs.getString(3);
		boolean forInstalation =rs.getBoolean(4);
		boolean forOrder =rs.getBoolean(5);
		boolean forReport =rs.getBoolean(6);
		boolean forSqueeze =rs.getBoolean(7);
		boolean forDealing =rs.getBoolean(8);
		final CityVO city = new CityVO(cityId, cityIdn, cityDesc);
		city.setForInstalation(forInstalation);
		city.setForOrder(forOrder);
		city.setForReport(forReport);
		city.setForSqueeze(forSqueeze);
		city.setForDealing(forDealing);
		return city;
	}

	public SimpleComboElement getCityByIdn(final String cityIdn) throws DaoException {
		
		SimpleComboElement city = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery("select cityIdn, cityDesc from City where cityIdn='" + cityIdn + "'");
			while(rs.next()){
				city = new SimpleComboElement(rs.getString(1),rs.getString(2));
			}
		} catch (SQLException e) {
			throw new DaoException("CityDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return city;
	}

	public String getCityIdnByDescription(String cityDesc) throws DaoException {
		String cityIdn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery("select cityIdn from City where cityDesc='" + cityDesc + "'");
			if(rs.next()){
				cityIdn = rs.getString(1);
			}
		} catch (SQLException e) {
			throw new DaoException("CityDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return cityIdn;
	}

	public void create(CityVO city) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			String query = "insert into City (cityIdn,cityDesc,forInstalation,forOrder,forReport,forSqueeze,forDealing) values(" +
					"'" + city.getCityIdn() + "'," +
					"'" + city.getCityDesc() + "'," +
					city.isForInstalation() + "," +
					city.isForOrder() + "," +
					city.isForReport() + "," +
					city.isForSqueeze() + "," +
					city.isForDealing() +
					")";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			log.error("CityDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
	}
	
}
