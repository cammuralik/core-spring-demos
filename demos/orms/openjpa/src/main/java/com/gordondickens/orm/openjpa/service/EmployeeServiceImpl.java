package com.gordondickens.orm.openjpa.service;

import com.gordondickens.orm.openjpa.domain.Employee;
import com.gordondickens.orm.openjpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public long countAllEmployees() {
        return employeeRepository.count();
    }

    public void deleteEmployee(final Employee employee) {
        employeeRepository.delete(employee);
    }

    public Employee findEmployee(final Long id) {
        return employeeRepository.findOne(id);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> findEmployeeEntries(final int firstResult, final int maxResults) {
        return employeeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

    public void saveEmployee(final Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee updateEmployee(final Employee employee) {
        return employeeRepository.save(employee);
    }
}
