package com.gordondickens.orm.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.gordondickens.orm.domain.Employee;
import com.gordondickens.orm.repository.EmployeeRepository;
import com.gordondickens.orm.service.EmployeeService;
import com.gordondickens.orm.service.EmployeeServiceImpl;

/**
 * User: gordon Date: 6/18/12 Time: 4:34 PM
 */
@Configuration
@ComponentScan(basePackages = { "com.gordondickens.orm" }, excludeFilters = { @ComponentScan.Filter(Configuration.class) })
@PropertySource("classpath:META-INF/spring/database.properties")
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
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Employee employee() {
		logger.debug("*** Creating Employee");
		return new Employee();
	}

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
	@DependsOn("dataSource")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws Exception {
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

	// @Bean
	// public JpaRepositoryFactory jpaRepository() throws Exception {
	// return new JpaRepositoryFactory(entityManager());
	// }

	@Bean
	@DependsOn("entityManagerFactory")
	public PlatformTransactionManager transactionManager() throws Exception {
		logger.debug("*** 3. Creating transactionManager");
		JpaTransactionManager bean = new JpaTransactionManager();
		bean.setEntityManagerFactory(entityManagerFactory().getObject());
		bean.setDataSource(dataSource());
		bean.afterPropertiesSet();

		return bean;
	}

	@Bean
	@DependsOn("transactionManager")
	public EntityManager entityManager() throws Exception {
		logger.debug("*** 4. Creating entityManager");
		if (entityManagerFactory() == null)
			logger.error("CEMF IS NULL");

		EntityManager bean = null;

		EntityManagerFactory entityManagerFactory = entityManagerFactory()
				.getObject();
		if (entityManagerFactory == null) {
			logger.error("EMF IS NULL");
			return null;
		} else {
			bean = entityManagerFactory.createEntityManager();
			if (bean == null) {
				logger.error("EM IS NULL");
				return null;
			}
		}

		return bean;
	}

	@Bean
	@DependsOn("entityManager")
	public JpaRepositoryNameSpaceHandler jpaRepositoryNameSpaceHandler() {
		logger.debug("*** 5. Creating jpaRepositoryNameSpaceHandler");
		JpaRepositoryNameSpaceHandler bean = new JpaRepositoryNameSpaceHandler();
		bean.init();

		logger.debug("*** Created '{}'", bean.toString());

		return bean;
	}

	@Bean
	public EmployeeRepository employeeRepository() throws Exception {
		logger.debug("*** 6. Creating employeeRepository");
		JpaRepositoryFactoryBean<EmployeeRepository, Employee, Long> factory = new JpaRepositoryFactoryBean<EmployeeRepository, Employee, Long>();
		factory.setBeanFactory(beanFactory);
		factory.setEntityManager(entityManager());
		factory.setRepositoryInterface(EmployeeRepository.class);
		factory.afterPropertiesSet();

		logger.debug("*** Created employeeRepository '{}'", factory.getObject()
				.toString());

		return factory.getObject();
		// return jpaRepository().getRepository(EmployeeRepository.class);
	}

	@Bean
	public EmployeeService employeeService() {
		logger.debug("*** 7. Creating employeeService");
		EmployeeService bean = new EmployeeServiceImpl();

		return bean;
	}

}
