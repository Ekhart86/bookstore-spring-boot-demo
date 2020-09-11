package com.bookstore.config;

import com.bookstore.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public RequestSpecification requestSpecification() {
        PreemptiveBasicAuthScheme auth = new PreemptiveBasicAuthScheme();
        auth.setUserName("test");
        auth.setPassword("test");
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(auth)
                .log(LogDetail.ALL)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Book firstBook() {
        return new Book(null,
                "First book",
                "First author",
                "First publication",
                "First category",
                100,
                1000);
    }

    @Bean
    public Book secondBook() {
        return new Book(null,
                "Second book",
                "Second author",
                "Second publication",
                "Second category",
                200,
                2000);
    }
}
