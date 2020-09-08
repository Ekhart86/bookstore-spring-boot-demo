package com.bookstore.config;

import com.bookstore.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Book firstBook() {
        return Book.builder()
                .setName("First book")
                .setAuthor("First author")
                .setPublication("First publication")
                .setCategory("First category")
                .setPages(100)
                .setPrice(1000)
                .build();
    }

    @Bean
    public Book secondBook() {
        return Book.builder()
                .setName("Second book")
                .setAuthor("Second author")
                .setPublication("Second publication")
                .setCategory("Second category")
                .setPages(200)
                .setPrice(2000)
                .build();
    }
}
