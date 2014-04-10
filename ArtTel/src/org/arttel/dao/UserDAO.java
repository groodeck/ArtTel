package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.entity.Order;
import org.arttel.exception.DaoException;
import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDAO extends BaseDao {

	@PersistenceContext
	private EntityManager em;
	
	public boolean checkUserPassword(String user, String hashedPassword)
			throws DaoException {

		if(user == null || hashedPassword==null){
			return false;
		}
		
		List<String> entityResults = em.createQuery(
				"select u.userPassword from User u where u.userName = '" + user + "'")
				.getResultList();
		
		for(String password : entityResults){
			if(hashedPassword.equals(password)) {
				return true;
			}
		}
		return false;
	}

	public Map<String,Boolean> getUserPrivileges(String user) throws DaoException {
		
		final Map<String,Boolean> userPrivileges = new HashMap<String,Boolean>();
		if( user == null ){
			return userPrivileges;
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt
					.executeQuery("select up.privilegeIdn " 
								+	"from UserPrivileges up "
								+	"join user us on up.userId=us.userId " 
								+	"where us.userName = '"
								+ 		user
								+	"'");
			while (rs.next()) {
				final String userPrivilege = rs.getString(1);
				userPrivileges.put(userPrivilege, Boolean.TRUE);
			}
		} catch (SQLException e) {
			throw new DaoException("UserDao SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		
		return userPrivileges;
	}

	public List<? extends ComboElement> getUserDictionary(boolean withEmptyOption) throws DaoException {
		
		List<ComboElement> userList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			userList.add(new EmptyComboElement());
		}
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery("select userName, userName from User");
			while(rs.next()){
				userList.add(new SimpleComboElement(rs.getString(1),rs.getString(2)));
			}
		} catch (SQLException e) {
			throw new DaoException("UserDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return userList;
	}
	
	public List<String> getUserList() throws DaoException {
		
		List<String> userList = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery("select userName from User");
			while(rs.next()){
				userList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new DaoException("UserDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return userList;
	}

}
