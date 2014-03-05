package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.exception.DaoException;
import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;
import org.springframework.stereotype.Component;

@Component
public class MachineDAO extends BaseDao {
	
	private static final String MACHINE_LIST_QUERY = "select machineId, name, registration from Machine";

	public List<ComboElement> getMachinesComboList(final boolean withEmptyOption) throws DaoException {
		
		List<ComboElement> machineList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			machineList.add(new EmptyComboElement());
		}
		Statement stmt = null;
		ResultSet rs = null;
		try {
			final String query = MACHINE_LIST_QUERY;
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				final ComboElement machine = extractMachineAsComboElement(rs);
				machineList.add(machine);
			}
		} catch (SQLException e) {
			throw new DaoException("DictionaryDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return machineList;
	}

	private ComboElement extractMachineAsComboElement(ResultSet rs) throws SQLException {
		final String machineId = rs.getString(1);
		final String machineLabel = getMachineLabel(rs);
		return new SimpleComboElement(machineId, machineLabel);
	}

	private String getMachineLabel(ResultSet rs) throws SQLException {
		final String machineLabel;
		final String name = rs.getString(2);
		final String registration = rs.getString(3);
		if(StringUtils.isNotEmpty(registration)){
			machineLabel = name +" "+ registration;
		} else {
			machineLabel = name;
		}
		return machineLabel;
	}

}
