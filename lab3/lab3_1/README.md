### 3.1)

**a) Identify a couple of examples that use AssertJ expressive methods chaining.**

```java
// givenSetOfEmployees_whenFindAll_thenReturnAllEmployees - Tests A
List<Employee> allEmployees = employeeRepository.findAll();
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
/*
Asserts that the list of returned employees has size 3 (3 employees in the list). Then extracts the names of each employee in the list and checks if their names are the same as the expected employees names.
*/

// Tests B
void whenValidName_thenEmployeeShouldExist() {
    boolean doesEmployeeExist = employeeService.exists("john");
    assertThat(doesEmployeeExist).isTrue();
    // ...
}
/*
Asserts that the boolean doesEmployeeExis returned by the service is True
 */

// givenEmployees_whenGetEmployees_thenStatus200 - Tests E
ResponseEntity<List<Employee>> response = restTemplate
        .exchange("/api/employees", HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        });
assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
/*
Asserts that the list of returned employees after executing an http GET method checks if the response has a status code of OK and gets the body of the response (List<Employee>), extracts the name of each element and confirms that those are exactly bob and alex.
*/
```
**b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).** In B_EmployeeService_UnitTest the repository is mocked using mockito which also is responsible for loading expectations and setting verifications. Relying only in JUnit + Mockito makes this test a unit test, much faster that using a full SpringBootTest. No database involved.
```java
class B_EmployeeService_UnitTest {

    @Mock(lenient = true)
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    // ...
}
```


**c) What is the difference between standard @Mock and @MockBean?**  
- **@Mock**
  ```java
  import org.mockito.Mock;
  ```
  - From the ``mockito`` library and is functionally equivalent to Mockito.mock() method
  - Short annotation for mocks creation
  - Makes the test class more readable.
  - Makes the verification error easier to read because the field name is used to identify the mock.
  - Note that we are required to enable Mockito annotations such as @Mock (and @InjectsMock) through the use of ``@ExtendWith(MockitoExtension.class)``
  - It is mannually injected into the class under test by using @InjectsMock annotation.
- **@MockBean**:
  ```java
  import org.springframework.boot.test.mock.mockito.MockBean;
  ```
  - From the ``spring-boot-test`` library;
  - Annotation that can be used to add mocks to a Spring ApplicationContext;
  - If a bean, compatible with the declared class exists in the context, it replaces it by the mock. If it is not the case, it adds the mock in the context as a bean. ``@MockBean`` replaces the actual beans in the Spring context with mock objects during testing, while ``@Mock`` does not impact the actual beans in the Spring context.
  - ``@MockBean`` is automatically initialized by the Spring Boot Test framework during the test context setup;

If a test that doesn't need any dependencies from the Spring Boot container, the classic/plain Mockito is the way to follow: it is fast and favors the isolation of the tested component.

If your test needs to rely on the Spring Boot container and you want also to add or mock one of the container beans : ``@MockBean`` from Spring Boot is the way.

**d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?**
- By using ``@AutoConfigureTestDatabase``, if a dependency to an embedded database is available, an in-memory database is set up (h2 included in POM)
- It is possible to configure integration tests with test-specific property sources. Declare the ``@TestPropertySource`` annotation on a test class to declare resource locations for test properties files or inlined properties.
- In the following case the test property file is ``application-integrationtest.properties``:
```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
// @TestPropertySource( locations = "application-integrationtest.properties")
class E_EmployeeRestControllerTemplateIT {
    // ...
}
```
```
## application-integrationtest.properties
spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo  ## URL for the MySQL database connection
spring.jpa.hibernate.ddl-auto=create-drop                   ## creates and drops the database schema each time the application starts and stops, respectively
spring.datasource.username=demo                             ## define username
spring.datasource.password=demo                             ## define password
```

**e) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?**
- **C** - by using ``@WebMvcTest`` mode we run the tests in a simplified and light environment, simulating the behavior of an application server. ``MockMvc`` provides an entry point to server-side testing. It performs full Spring MVC request handling but via mock request and response objects without starting a full HTTP server. Neither repository component nor database is involved.
```java
@WebMvcTest(EmployeeRestController.class)
class C_EmployeeController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;    //entry point to the web framework

    @MockBean
    private EmployeeService service;

    // ..
}
```
- **D** - Start the full web context (``@SpringBootTest``, with Web Environment enabled). The API is deployed into the normal SpringBoot context. Use the entry point for server-side Spring MVC test support (``MockMvc``). Here several components will participate (the REST endpoint, the service implementation, the repository, and the database).
```java
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = EmployeeMngrApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class D_EmployeeRestControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository repository;

    // ..
}
```
- **E** - Start the full web context (``@SpringBootTest``, with Web Environment enabled) - on a random port. The API is deployed into the normal SpringBoot context. Use a REST client to create realistic requests (``TestRestTemplate``). Similar to the previous case, but instead of assessing a convenient servlet entry point for tests, uses an API client (so request and response un/marshaling will be involved).
```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class E_EmployeeRestControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository repository;

    // ..
}
```
**Summary**
- **C** - ``MockMVC`` on a lightweight environment
- **D** - ``MockMVC`` on a full web context
- **E** - full API client (``TestRestTemplate``) on a full web context