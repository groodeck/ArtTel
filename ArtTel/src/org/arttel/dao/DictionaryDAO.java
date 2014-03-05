package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.taglibs.standard.lang.jstl.EmptyOperator;
import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.dictionary.DictionaryType;
import org.arttel.exception.DaoException;
import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xml.internal.serialize.OutputFormat.DTD;

@Component
public class DictionaryDAO extends BaseDao {
	
	private static final Map<DictionaryType, String> queryMap;
	
	static {
		queryMap = new HashMap<DictionaryType, String>();
		queryMap.put(DictionaryType.FUEL, "select fuel from fueldict");
	}

	public List<ComboElement> getDictionaryValues(final DictionaryType dictionaryType, final boolean withEmptyOption) throws DaoException {
		
		List<ComboElement> dictionaryValues = new ArrayList<ComboElement>();
		if(withEmptyOption){
			dictionaryValues.add(new EmptyComboElement());
		}
		Statement stmt = null;
		ResultSet rs = null;
		try {
			final String query = queryMap.get(dictionaryType);
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				dictionaryValues.add(new SimpleComboElement(rs.getString(1),rs.getString(1)));
			}
		} catch (SQLException e) {
			throw new DaoException("DictionaryDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return dictionaryValues;
	}

}
