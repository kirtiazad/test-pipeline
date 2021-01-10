package com.systelab.patient.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PatientControllerTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/patients")
          .then()
             .statusCode(200);
    }

}