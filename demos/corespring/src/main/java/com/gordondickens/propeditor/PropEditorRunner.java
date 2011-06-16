package com.gordondickens.propeditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gordondickens.sample.Customer;

public class PropEditorRunner {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(PropEditorRunner.class);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF/spring/prop-editor-context.xml");

		Customer customer = (Customer) applicationContext.getBean("customer");

		logger.debug("CUSTOMER DETAIL: " + customer.toString());

	}

}
