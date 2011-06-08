package com.gordondickens.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author gordon
 * 
 */
@ContextConfiguration(value = "/META-INF/spring/bean-post-processing-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BeanPostProcessingTests {
	private final Logger logger = LoggerFactory
			.getLogger(BeanPostProcessingTests.class);

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void showUtilsInfo() {
		ExampleServiceToo exampleServiceToo = applicationContext
				.getBean(ExampleServiceToo.class);
		assertNotNull(exampleServiceToo);
	}
}
