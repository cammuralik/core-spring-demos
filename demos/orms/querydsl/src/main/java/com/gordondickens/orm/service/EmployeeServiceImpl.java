package com.gordondickens.orm.service;

import com.gordondickens.orm.domain.Employee;
import com.gordondickens.orm.domain.QEmployee;
import com.gordondickens.orm.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory
            .getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeRepository employeeRepository;

    static final QEmployee queryEmployee = QEmployee.employee;

    @Override
    public long countAllEmployees() {
        return employeeRepository.count();
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public Employee findEmployee(Long id) {
        return employeeRepository.findOne(id);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findEmployeeEntries(int firstResult, int maxResults) {
        return employeeRepository.findAll(
                new org.springframework.data.domain.PageRequest(firstResult
                        / maxResults, maxResults)).getContent();
    }

    @Override
    public void saveEmployee(Employee employee) {
        Employee e2 = employeeRepository.save(employee);
        logger.debug("Employee received & saved '{}'", employee.toString());
        logger.debug("Employee returned from repository '{}'", e2.toString());
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findByLastName(String search) {
        return employeeRepository.findByEmployeeLastNameContains(search);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findEmployeeLastNameILike(String search) {
        search = trimToEmpty(search).toLowerCase();

        List<Employee> employees = new ArrayList<Employee>();
        Iterable<Employee> c = employeeRepository.findAll(queryEmployee.lastName.containsIgnoreCase("icke"));
        for (Employee employee : c) {
            employees.add(employee);
        }

        return new ArrayList<Employee>();
    }


}
