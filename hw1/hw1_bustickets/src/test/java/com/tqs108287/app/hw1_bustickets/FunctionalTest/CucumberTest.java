package com.tqs108287.app.hw1_bustickets.FunctionalTest;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/tqs108287/App")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com/tqs108287/App")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // Default 8080
@Testcontainers
@TestPropertySource(locations = "classpath:application-it.properties")
public class CucumberTest {
    static final Logger logger = getLogger(lookup().lookupClass());

    @Container
    public static MySQLContainer container = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
            .withUsername("miguel")
            .withPassword("password")
            .withDatabaseName("test");

    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @BeforeEach
    public void setUp(){
        // data inserted through data_test.sql -> no setup required
    }

    @Test
    void name() {
        assertTrue(false);
    }
}
