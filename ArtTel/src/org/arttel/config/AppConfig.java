package org.arttel.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@ComponentScan(basePackages = "org.arttel")
@EnableTransactionManagement
public class AppConfig {

	private String dbms="mysql";
	private String serverName="localhost";
	private String portNumber="3306";
	private String userName = "arttelDBuser";
	private String password = "arttelDBpassword";
	private String dbName = "arttel";
	
	@Bean
	public DataSource dataSource() {
        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:" + this.dbms + "://" + this.serverName +
	    				":" + this.portNumber + "/"+this.dbName+"?useUnicode=true&characterEncoding=utf8");
        dataSource.setUser(this.userName);
        dataSource.setPassword(this.password);
		return dataSource;
    }
}