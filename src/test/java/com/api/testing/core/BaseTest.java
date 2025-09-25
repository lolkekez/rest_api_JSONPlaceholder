package com.api.testing.core;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {
    
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification successResponseSpec;
    protected static ResponseSpecification createdResponseSpec;
    protected static ResponseSpecification errorResponseSpec;
    
    @BeforeAll
    static void setupRestAssured() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        requestSpec = new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        successResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        createdResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        errorResponseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
