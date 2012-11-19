package com.gordondickens.orm.config;

import com.gordondickens.orm.domain.Employee;
import com.gordondickens.orm.domain.QEmployee;
import com.gordondickens.orm.repository.EmployeeRepository;
import com.gordondickens.orm.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = RepositoryConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RepositoryConfigTest {
    private static final Logger logger = LoggerFactory
            .getLogger(RepositoryConfig.class);


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ApplicationContext appContext;

    static final QEmployee queryEmployee = QEmployee.employee;


    @Test
    public void listAppContextBeans() {
        String[] beans = appContext.getBeanDefinitionNames();
        for (String bean : beans) {
            logger.trace("Bean '{}'", bean);
        }
    }


    @Test
    public void testEmployeeRepository() {
        assertThat("Employee Repository MUST exist", employeeRepository, notNullValue());
        logger.debug(" ****** Employee Repository '{}' ******",
                employeeRepository.toString());

        Employee employee = appContext.getBean(Employee.class);
        employee.setFirstName("gordon");
        employee.setLastName("Dickens");

        employeeService.saveEmployee(employee);
        logger.debug(employee.toString());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees, notNullValue());
        logger.debug("{} Employees Found", employees.size());
        assertThat(employees.size(), greaterThanOrEqualTo(1));
        for (Employee emp : employees) {
            logger.debug("FOUND '{}'", emp.toString());
        }
    }

    @Test
    public void verifyFindLastNameCaseInsensitive() {
        Employee employee = new Employee();
        employee.setComments("blah");
        employee.setFirstName("Nodrog");
        employee.setLastName("Chickens");
        employeeRepository.saveAndFlush(employee);

        List<Employee> employees = new ArrayList<Employee>();
        Iterable<Employee> c = employeeRepository.findAll(queryEmployee.lastName.containsIgnoreCase("icke"));
        assertThat(c, notNullValue());
        for (Employee employee2 : c) {
            employees.add(employee2);
        }
        assertThat(employees, notNullValue());
        assertThat(employees.size(), greaterThanOrEqualTo(1));

    }

}
