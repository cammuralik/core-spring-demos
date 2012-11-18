package com.gordondickens.hiburrdemo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ContextConfiguration("classpath*:/META-INF/spring/*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
//@TransactionConfiguration(defaultRollback = false)
@ActiveProfiles(profiles = "derby")
public class CustomerTest {

    private Logger logger = LoggerFactory.getLogger(CustomerTest.class);

    @Autowired
    private SessionFactory factory;

    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    protected void setTemplate(DataSource datasource) {
        template = new JdbcTemplate(datasource);
    }

    private JdbcTemplate template;

    /**
     * Create customers, within a separate transaction. These customers will be
     * available for the length of the test cycle
     */
    @BeforeTransaction
    public void setupManyCustomers() {
        int results = template.update("delete from Customer");
        logger.debug("Before Transaction Deleted {} Customer(s)", results);
        Session session = factory.openSession();
        for (int i = 0; i < 5; i++) {
            Customer c = new Customer();
            c.setFirstName("Customer #" + (i + 1));
            c.setLastName("Person");
            session.save(c);
        }
        session.flush();
        session.close();
    }

    @Test
    @Transactional
    public void introspectStatistics() {
        Statistics statistics = factory.getStatistics();
        logger.trace(statistics.toString());
    }

    @Transactional
    @Test
    @Rollback(true)
    public void addAndVerifyCustomer() {
        logger.debug("***** Testing Customer in Cache ******");

        Session session = factory.getCurrentSession();
        Customer c = new Customer();
        c.setFirstName("Phil");
        c.setLastName("ADelphia");

        session.save(c);
        assertThat("Initial Customer Must be Valid",
                c.getId(), notNullValue());

        session.flush();
        session.clear();

        Customer c2 = (Customer) session.load(Customer.class, c.getId());

        assertThat("Customer Loaded into Session by ID Must Exist",
                c2.getId(), notNullValue());
        assertThat("Customer Loaded into Session by ID Must Match Initial Customer",
                c.getId(), equalTo(c2.getId()));
        assertThat("Initial Customer Entity in Cache Must not be Same as Customer Loaded by ID",
                c, not(sameInstance(c2)));

        /* session.flush(); */
    }


    @Test
    @Transactional
    public void testCached() {
        logger.debug("***** Testing Customer in Cache ******");
        Session session = factory.getCurrentSession();
        Customer c = (Customer) session.createQuery(
                "select c from Customer c "
                        + "where c.id = (select min(c2.id) from Customer c2)")
                .uniqueResult();
        assertThat(c, notNullValue());
        assertThat("Customer Entity Must Exist in Cache",
                factory.getCache().containsEntity(Customer.class, c.getId()));
    }

    @Test
    @Transactional
    public void testNotCached() {
        logger.debug("***** Testing Product in Cache ******");
        Session session = factory.getCurrentSession();
        Product p = new Product();
        p.setListPrice(new BigDecimal("100.00"));
        p.setName("The Widget");
        session.save(p);
        session.flush();
        assertFalse("Product Entity Must Exist in Cache",
                factory.getCache().containsEntity(Product.class, p.getId()));
    }

    @Test
    @Transactional
    public void testSize() {
        logger.debug("***** Testing Customer Count in Cache ******");

        Session session = factory.getCurrentSession();
        Long size = (Long) session.createQuery(
                "select count(c) from Customer c").uniqueResult();
        assertThat("Exactly 5 Records Must Exist",
                (long) 5, equalTo(size));

    }
}
