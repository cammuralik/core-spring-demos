package com.gordondickens.orm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gordondickens.orm.domain.Employee;
import com.gordondickens.orm.repository.EmployeeRepository;

@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	private static final Logger logger = LoggerFactory
			.getLogger(EmployeeServiceImpl.class);
	@Autowired
	EmployeeRepository employeeRepository;

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
}
