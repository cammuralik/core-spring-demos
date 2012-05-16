Mocking Demo for Spring
-----------------------



This demo illustrates the use of Mockito for mocking cross layer dependencies.

## The primary test for Mocking is in _src/test/java/com.gordondickens.itemmanager.controller.ItemControllerTest.java_
This test illustrates simple mock tests of the controller calling the ItemService object.

### Mocking helps with 2 facets:
1. Dynamic stubbing of dependent beans
2. Behavior verification of service invocation
3. See the test: _testMultipleDeletesOccur_ for an example of verifying that an undetermined number of occurrances of the _delete_ method occur.

## Mocking Limitations
 - Mocks do not work on _static_ or _final_

### To Mock _static_ or _final_ we need PowerMock
 - See the example _src/test/java/com.gordondickens.itemmanager.service.MyBeanPowerMockTest_



### Notes:

 - When using the @Mock annotation, it is required to call _MockitoAnnotations.initializeMocks(testClass)_ or use _@RunWith(MockitoJUnit4Runner)
 - Another technique is to use Spring configuration to define a factory method call to generate a mocked class.
   \<bean id="myMockedBean" class="org.mockito.Mockito" factory-method="mock"\>
       \<constructor-arg value="com.gordondickens.mypackage.MyBean" /\>
   \</bean\>


