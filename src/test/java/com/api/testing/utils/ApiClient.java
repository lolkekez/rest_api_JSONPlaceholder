package com.api.testing.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

/**
 * Утилитный класс для выполнения HTTP запросов
 * Инкапсулирует логику работы с REST Assured
 */
public class ApiClient {
    
    private final RequestSpecification requestSpec;
    
    public ApiClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }
    
    /**
     * Выполняет GET запрос
     */
    public Response get(String endpoint) {
        return given(requestSpec)
                .when()
                .get(endpoint);
    }
    
    /**
     * Выполняет GET запрос с параметрами
     */
    public Response get(String endpoint, String paramName, Object paramValue) {
        return given(requestSpec)
                .queryParam(paramName, paramValue)
                .when()
                .get(endpoint);
    }
    
    /**
     * Выполняет POST запрос с телом
     */
    public Response post(String endpoint, Object body) {
        return given(requestSpec)
                .body(body)
                .when()
                .post(endpoint);
    }
    
    /**
     * Выполняет PUT запрос с телом
     */
    public Response put(String endpoint, Object body) {
        return given(requestSpec)
                .body(body)
                .when()
                .put(endpoint);
    }
    
    /**
     * Выполняет PATCH запрос с телом
     */
    public Response patch(String endpoint, Object body) {
        return given(requestSpec)
                .body(body)
                .when()
                .patch(endpoint);
    }
    
    /**
     * Выполняет DELETE запрос
     */
    public Response delete(String endpoint) {
        return given(requestSpec)
                .when()
                .delete(endpoint);
    }
    
    /**
     * Выполняет запрос и проверяет ответ по спецификации
     */
    public Response executeAndValidate(String method, String endpoint, Object body, ResponseSpecification responseSpec) {
        Response response;
        
        switch (method.toUpperCase()) {
            case "GET":
                response = get(endpoint);
                break;
            case "POST":
                response = post(endpoint, body);
                break;
            case "PUT":
                response = put(endpoint, body);
                break;
            case "PATCH":
                response = patch(endpoint, body);
                break;
            case "DELETE":
                response = delete(endpoint);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        
        return response.then().spec(responseSpec).extract().response();
    }
}
