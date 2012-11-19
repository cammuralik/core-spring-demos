package com.gordondickens.orm.repository;

import com.gordondickens.orm.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository
        extends JpaSpecificationExecutor<Employee>,
        JpaRepository<Employee, Long>,
        QueryDslPredicateExecutor<Employee> {

    //Automatic Query Generation with Contains
    public List<Employee> findByEmployeeLastNameContains(String search);
}
