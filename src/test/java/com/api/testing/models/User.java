package com.api.testing.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    
    private Integer id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        private String street;
        private String city;
        private String suite;
        private String zipcode;
        private Geo geo;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Geo {
        private String lat;
        private String lng;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Company {
        private String name;
        private String catchPhrase;
        private String bs;
    }

    public boolean isValidEmail() {
        return email != null && email.contains("@") && email.contains(".");
    }
}
