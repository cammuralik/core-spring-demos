package com.gordondickens.orm.config;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.gordondickens.orm.domain.Employee;
import com.gordondickens.orm.repository.EmployeeRepository;
import com.gordondickens.orm.service.EmployeeService;

/**
 * User: gordon Date: 6/18/12 Time: 4:40 PM
 */
@ContextConfiguration(classes = RepositoryConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryConfigTests {
	private static final Logger logger = LoggerFactory
			.getLogger(RepositoryConfig.class);

	@Autowired
	JpaRepositoryNameSpaceHandler jpaRepositoryNameSpaceHandler;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ApplicationContext appContext;

	@Test
	public void listAppContextBeans() {
		String[] beans = appContext.getBeanDefinitionNames();
		for (String bean : beans) {
			logger.trace("Bean '{}'", bean);
		}
	}

	@Test
	public void testRepositoryConfig() {
		assertNotNull("JPA Repository NS Handler MUST exist",
				jpaRepositoryNameSpaceHandler);
		logger.debug(jpaRepositoryNameSpaceHandler.toString());
	}

	@Test
	public void testEmployeeRepository() {
		assertNotNull("Employee Repository MUST exist", employeeRepository);
		logger.debug(" ****** Employee Repository '{}' ******",
				employeeRepository.toString());

		Employee employee = appContext.getBean(Employee.class);
		employee.setFirstName("gordon");
		employee.setLastName("Dickens");

		employeeService.saveEmployee(employee);
		logger.debug(employee.toString());

		// Employee e2 = employeeRepository.save(employee);
		// logger.debug(e2.toString());

		List<Employee> employees = employeeRepository.findAll();
		logger.debug("{} Employees Found", employees.size());
		for (Employee emp : employees) {
			logger.debug("FOUND '{}'", emp.toString());
		}

	}

}
