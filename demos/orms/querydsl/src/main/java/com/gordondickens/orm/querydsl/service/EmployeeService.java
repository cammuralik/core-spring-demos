package com.gordondickens.orm.querydsl.service;

import com.gordondickens.orm.querydsl.domain.Employee;

import java.util.List;

public interface EmployeeService {

    public abstract long countAllEmployees();

    public abstract void deleteEmployee(final Employee employee);

    public abstract Employee findEmployee(final Long id);

    public abstract List<Employee> findAllEmployees();

    public abstract List<Employee> findEmployeeEntries(final int firstResult, final int maxResults);

    public abstract void saveEmployee(final Employee employee);

    public abstract Employee updateEmployee(final Employee employee);

    public abstract List<Employee> findByLastName(final String search);

    public abstract List<Employee> findEmployeeLastNameILike(final String search);
}
