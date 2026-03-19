package com.demoworks.demodelivery.courier.management.api.controller;

import com.demoworks.demodelivery.courier.management.domain.model.Courier;
import com.demoworks.demodelivery.courier.management.domain.repository.CourierRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourierControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CourierRepository repository;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/couriers";
    }

    @Test
    void should_return_201_when_trying_to_create_new_courier() {
        String requestBody = """
                {
                    "name": "Joao Silva",
                    "phone": "11987876543"
                }
                    """;

        RestAssured
                .given()
                    .body(requestBody)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("id", Matchers.notNullValue())
                    .body("name", Matchers.equalTo("Joao Silva"));
    }

    @Test
    void should_return_200_when_trying_to_get_courier_by_id() {
        UUID courierId = repository.saveAndFlush(
                Courier.brandNew("Will Smith", "11987654321")
        ).getId();

        RestAssured
                .given()
                    .pathParam("courierId", courierId)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{courierId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", Matchers.equalTo(courierId.toString()))
                    .body("name", Matchers.equalTo("Will Smith"))
                    .body("phone", Matchers.equalTo("11987654321"));

    }
}