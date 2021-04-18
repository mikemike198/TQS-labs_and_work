# 1

## a)

Test Class EmployeeRestControllerIT:
    assertThat(found).extracting(Employee::getName).containsOnly("bob");
    
Test Class EmployeeRestControllerTemplateIT:
    assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");

## b)

In the Test Class EmployeeService_UnitTest, its used Mock to mock the behavior of the repository, and its injected in the EmployeeService.

## c)

Simple and Plain Mock is used in Unit Test that dont rely in the Spring Boot IoC container, its used simply to mock the behaviour of an object without instantiate it. Normally are used because they are fast, wich is a requirement in Unit Tests.

MockBean is used when we need to mock a bean form Spring Boot. The way we use them is equal to the way we use the normal mock objects, but they mock a bean and consequently their dependencies, not only its class. They are heavier than the normal mocks, but their are usefull for example, when we want to test our web context without having to run a full Spring Boot test, like @WebMVCTest.

## d)

The application-integrationtest.properties file is used to save informations that are important to Spring Boot to perfom the test with the right propreties. It is used when we are doing full integration test, top to bottom. 