/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gordondickens.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleConfigurationTests {
	Logger logger = LoggerFactory.getLogger(ExampleConfigurationTests.class);

	@Autowired
	private Service service;

	@Autowired
	private MyExceptionThrower myExceptionThrower;

	@Test
	public void testSimpleProperties() throws Exception {
		assertNotNull(service);
	}

	@Test
	public void testExceptionAOP() throws Exception {
		logger.debug("ThrowingExceptionOne");
		myExceptionThrower.ThrowExceptionOnePlease();
	}

	@Test
	public void testExceptionAOP2() throws Exception {
		logger.debug("ThrowingExceptionTwo");
		myExceptionThrower.ThrowExceptionTwoPlease();
	}

}