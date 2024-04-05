package com.tqs108287.app.lab7_3testContainers;

import com.tqs108287.app.lab7_3testContainers.data.Employee;
import com.tqs108287.app.lab7_3testContainers.data.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
@SpringBootTest
class Lab73testContainersIT {

	@Container
	public static PostgreSQLContainer container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
			.withUsername("miguel")
			.withPassword("password")
			.withDatabaseName("test");

	@Autowired
	private EmployeeRepository employeeRepository;

	// requires Spring Boot >= 2.2.6
	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}

	@Test
	void basicTest() {

		Employee emp = new Employee("Miguel", "miguel.belchior@ua.pt");
		employeeRepository.save(emp);

		assertEquals(emp.getId(), employeeRepository.findByEmail("miguel.belchior@ua.pt").getId());
		assertThat(employeeRepository.findAll()).extracting(Employee::getEmail).contains("diogo@gmail.com", "joao@gmail.com", "rafa@gmail.com", "miguel.belchior@ua.pt");
	}



}
