package com.gordondickens.itemmanager.transactions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@ContextConfiguration("classpath:/MyTransactionTest-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
public class MyTransactionTest {
    private static final Logger logger = LoggerFactory.getLogger(MyTransactionTest.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    public void testMe() {
        assertEquals(4, getCount());
        insertNewRow();
        assertEquals(5, getCount());
    }

    @Transactional(readOnly = false)
    private void insertNewRow() {
        logger.debug("*** Preparing to insert new Row");
        String sql = "insert into MY_ACCOUNT (A_NUMBER, A_NAME) values ('988', 'The Dickenseses');";

        try {
            errorChucker();
        } catch (RuntimeException e) {
            logger.error("D'oh", e);
        }
        logger.debug("*** Inserting new Row");
        jdbcTemplate.update(sql);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void errorChucker() throws RuntimeException {
        throw new RuntimeException("Chucked ERROR");
    }

    @Transactional(readOnly = true)
    private int getCount() {
        String sql = "select count(ID) from MY_ACCOUNT;";
        return jdbcTemplate.queryForInt(sql);
    }


}
