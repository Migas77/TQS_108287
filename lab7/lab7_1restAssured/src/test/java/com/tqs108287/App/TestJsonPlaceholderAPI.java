package com.tqs108287.App;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.*;

public class TestJsonPlaceholderAPI {

    @Test
    void listAllToDosAvailable() {
        given().
        when().
                get("https://jsonplaceholder.typicode.com/todos").
        then().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    void whenQueryingTodo4_thenReturnEtPorroTempora() {
        given().
        when().
                get("https://jsonplaceholder.typicode.com/todos/4").
        then().
                statusCode(200).
                body("title", equalTo("et porro tempora"));
    }

    @Test
    void whenListingTodos_ThenReturnId198ANd199() {
        given().
        when().
            get("https://jsonplaceholder.typicode.com/todos").
        then().
            statusCode(200).
            body("id", hasItems(198, 199));
    }

    @Test
    void whenListingTodos_GetResultsInLessThan2Seconds() {
        given().
        when().
            get("https://jsonplaceholder.typicode.com/todos").
        then().
            statusCode(200).
            time(lessThan(2L), SECONDS);
    }
}
