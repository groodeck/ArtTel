package org.arttel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class BaseDao {

	Logger log = Logger.getLogger(BaseDao.class);
	
	private String dbms="mysql";
	private String serverName="localhost";
	private String portNumber="3306";
	private String userName = "arttelDBuser";
	private String password = "arttelDBpassword";
	private String dbName = "arttel";

	private Connection conn = null;
	
	private void connect(){
		
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", this.userName);
	    connectionProps.put("password", this.password);

	    try {
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();

	    	conn = DriverManager.
	    		getConnection("jdbc:" + this.dbms + "://" + this.serverName +
	    				":" + this.portNumber + "/"+this.dbName+"?useUnicode=true&characterEncoding=utf8", connectionProps);
	    	log.debug("Connected to database");
		} catch (SQLException e) {
			log.error("SQLException", e);
		} catch (InstantiationException e) {
			log.error("InstantiationException", e);
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException", e);
		} catch (ClassNotFoundException e) {
			log.error("ClassNotFoundException", e);
		}
	}
	    
	protected Connection getConnection(){
		if(conn == null) {
			connect();
		}
		return conn;
	}
	
	protected void disconnect(Statement stmt, ResultSet rs) {
		try {
			if(stmt != null){
					stmt.close();
				stmt = null;
			}
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			log.error("SQLException", e);
		}
	}
}
