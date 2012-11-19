package com.gordondickens.orm.config;

import com.gordondickens.orm.domain.Employee;
import com.gordondickens.orm.repository.EmployeeRepository;
import com.gordondickens.orm.service.EmployeeService;
import com.gordondickens.orm.service.EmployeeServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * User: gordon Date: 6/18/12 Time: 4:34 PM
 */
@Configuration
@ComponentScan(basePackages = { "com.gordondickens.orm" },
        excludeFilters = { @ComponentScan.Filter(Configuration.class) })
@PropertySource("classpath:META-INF/spring/database.properties")
@EnableJpaRepositories("com.gordondickens.orm.repository")
@EnableTransactionManagement
public class RepositoryConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(RepositoryConfig.class);

	@Value("#{ environment['database.driverClassName']?:'' }")
	private String dbDriverClass;
	@Value("#{ environment['database.url']?:'' }")
	private String dbUrl;
	@Value("#{ environment['database.username']?:'' }")
	private String dbUserName;
	@Value("#{ environment['database.password']?:'' }")
	private final String dbPassword = "";
	@Value("#{ environment['database.vendor']?:'' }")
	private String dbVendor;

	@Autowired
	BeanFactory beanFactory;

	@Autowired
	private AbstractEnvironment environment;

	@Bean
	public DataSource dataSource() {
		logger.debug("*** 1. Creating dataSource");

		logger.trace("URL '{}'", dbUrl);
		logger.trace("Driver '{}'", dbDriverClass);
		logger.trace("UserName '{}'", dbUserName);
		logger.trace("dbPassword '{}'", dbPassword);
		BasicDataSource bean = new BasicDataSource();
		bean.setPassword(dbPassword);
		bean.setUrl(dbUrl);
		bean.setUsername(dbUserName);
		bean.setDriverClassName(dbDriverClass);
		bean.setTestOnBorrow(true);
		bean.setTestOnReturn(true);
		bean.setTestWhileIdle(true);
		bean.setTimeBetweenEvictionRunsMillis(1800000);
		bean.setMinEvictableIdleTimeMillis(1800000);
		bean.setNumTestsPerEvictionRun(3);
		return bean;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		logger.debug("*** 2. Creating entityManagerFactory");
		logger.trace("Vendor '{}'", dbVendor);

		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource());
		logger.debug("Scanning Package '{}' for entities", Employee.class
				.getPackage().getName());
		bean.setPackagesToScan(Employee.class.getPackage().getName());

		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.valueOf(dbVendor));
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setGenerateDdl(true);

		bean.setJpaVendorAdapter(jpaVendorAdapter);

		// No persistence.xml - thanks to packagesToScan
		return bean;
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		logger.debug("*** 3. Creating transactionManager");
		JpaTransactionManager bean = new JpaTransactionManager();
		bean.setEntityManagerFactory(entityManagerFactory().getObject());
		bean.setDataSource(dataSource());
		bean.afterPropertiesSet();

		return bean;
	}
}
