package com.api.testing.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {
    
    private final RequestSpecification requestSpec;
    
    public ApiClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response get(String endpoint) {
        return given(requestSpec)
                .when()
                .get(endpoint);
    }

    public Response get(String endpoint, String paramName, Object paramValue) {
        return given(requestSpec)
                .queryParam(paramName, paramValue)
                .when()
                .get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return given(requestSpec)
                .body(body)
                .when()
                .post(endpoint);
    }

    public Response put(String endpoint, Object body) {
        return given(requestSpec)
                .body(body)
                .when()
                .put(endpoint);
    }

    public Response patch(String endpoint, Object body) {
        return given(requestSpec)
                .body(body)
                .when()
                .patch(endpoint);
    }

    public Response delete(String endpoint) {
        return given(requestSpec)
                .when()
                .delete(endpoint);
    }

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
