package org.arttel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "org.arttel")
@EnableTransactionManagement
public class AppConfig {

	@Bean
	public PlatformTransactionManager getPlatformTransactionManager(){
		return new JpaTransactionManager();
	}
	
}