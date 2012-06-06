package com.gordondickens.sijms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: gordon Date: 4/16/11 Time: 12:46 PM
 */
@ContextConfiguration("JmsSenderTests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JmsSenderTests {
    private static final Logger logger = LoggerFactory.getLogger(JmsSenderTests.class);
    @Autowired
    MyJmsGateway myJmsGateway;

    @Test
    public void testJmsSend() {
        logger.debug("***** START TEST *****");
        myJmsGateway.sendMyMessage("myHeaderValue", "MY PayLoad");
        myJmsGateway.sendMyMessage("MY Other PayLoad");
        logger.debug("***** END TEST *****");
    }
}
