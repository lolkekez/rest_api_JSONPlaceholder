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

/**
 * Базовый класс для всех тестов API
 * Содержит общие настройки для REST Assured
 */
public abstract class BaseTest {
    
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification successResponseSpec;
    protected static ResponseSpecification errorResponseSpec;
    
    @BeforeAll
    static void setupRestAssured() {
        // Базовая конфигурация
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        
        // Спецификация для запросов
        requestSpec = new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        
        // Спецификация для успешных ответов
        successResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        
        // Спецификация для ответов с ошибками
        errorResponseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
