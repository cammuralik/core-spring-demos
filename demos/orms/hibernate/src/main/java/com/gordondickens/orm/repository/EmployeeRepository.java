package com.gordondickens.orm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gordondickens.orm.domain.Employee;

@Repository
public interface EmployeeRepository
        extends JpaSpecificationExecutor<Employee>,
          JpaRepository<Employee, Long> {
}
