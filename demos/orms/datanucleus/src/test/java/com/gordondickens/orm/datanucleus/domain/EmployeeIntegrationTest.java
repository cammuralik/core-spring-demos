package com.gordondickens.orm.datanucleus.domain;

import com.gordondickens.orm.datanucleus.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeIntegrationTest.class);

    @Autowired
    EmployeeService employeeService;

    @Test
    public void testMarkerMethod() {
        Employee employee = new Employee();
        employee.setFirstName("Cletus");
        employee.setLastName("Fetus");

        employeeService.saveEmployee(employee);
        assertThat("Employee MUST exist", employee, notNullValue());
        assertThat("Employee MUST have PK", employee.getId(), notNullValue());
        logger.debug("Employee {} Saved", employee.getId());


        Employee employee1 = employeeService.findEmployee(employee.getId());
        assertThat("Employee Must be Found by ID", employee1.getId(), samePropertyValuesAs(employee.getId()));
    }
}

