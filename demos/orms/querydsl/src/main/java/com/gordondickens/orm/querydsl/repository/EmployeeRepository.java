package com.gordondickens.orm.querydsl.repository;

import com.gordondickens.orm.querydsl.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository
        extends JpaSpecificationExecutor<Employee>,
        JpaRepository<Employee, Long>,
        QueryDslPredicateExecutor<Employee> {

    //Automatic Query Generation with Contains
    public List<Employee> findByEmployeeLastNameContains(String search);
}
