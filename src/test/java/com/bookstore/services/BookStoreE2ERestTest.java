package com.bookstore.services;

import com.bookstore.config.TestConfig;
import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("/test.properties")
@Import(TestConfig.class)
public class BookStoreE2ERestTest {

    @Value("${test.url}")
    private String testPath;

    @Autowired
    private BookRepository bookRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private RequestSpecification requestSpecification;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    @Qualifier(value = "firstBook")
    private Book firstBook;

    @Autowired
    @Qualifier(value = "secondBook")
    private Book secondBook;

    @Before
    public void tearDown() {
        bookRepository.save(firstBook);
    }

    @Test
    public void getAllBooksE2ERestTest() {
        Response response = given()
                .spec(requestSpecification)
                .get(testPath + port + "/api/books");
        assertThat(response.statusCode(), is(200));
        Book[] arrayBooks = response.as(Book[].class);
        assertThat(Collections.singletonList(arrayBooks.length), hasSize(1));
        assertThat(arrayBooks[0].getName(), is("First book"));
        assertThat(arrayBooks[0].getCategory(), is("First category"));
    }

    @Test
    public void createBooksE2ERestTest() throws JsonProcessingException {
        Response response = given()
                .spec(requestSpecification)
                .body(mapper.writeValueAsString(secondBook))
                .post(testPath + port + "/api/books");
        assertThat(response.statusCode(), is(200));
        Book createdBook = response.as(Book.class);
        assertThat(createdBook.getName(), is("Second book"));
        assertThat(createdBook.getCategory(), is("Second category"));
        Book bookFromDB = bookRepository.findByName("Second book").orElseThrow(NoSuchElementException::new);
        assertThat(bookFromDB.getAuthor(), is("Second author"));
        assertThat(bookFromDB.getPrice(), is(2000));
    }

    @Test
    public void updateBooksE2ERestTest() throws JsonProcessingException {
        firstBook.setAuthor("Update author");
        firstBook.setCategory("Update category");
        Response response = given()
                .spec(requestSpecification)
                .body(mapper.writeValueAsString(firstBook))
                .put(testPath + port + "/api/books/" + firstBook.getId());
        assertThat(response.statusCode(), is(200));
        Book updatedBook = response.as(Book.class);
        assertThat(updatedBook.getAuthor(), is("Update author"));
        assertThat(updatedBook.getCategory(), is("Update category"));
        Book bookFromDB = bookRepository.findByName("First book").orElseThrow(NoSuchElementException::new);
        assertThat(bookFromDB.getAuthor(), is("Update author"));
        assertThat(bookFromDB.getCategory(), is("Update category"));
    }

    @Test
    public void deleteBooksE2ERestTest() {
        List<Book> booksBeforeDelete = bookRepository.findAll();
        assertThat(booksBeforeDelete, hasSize(1));
        Response response = given()
                .spec(requestSpecification)
                .delete(testPath + port + "/api/books/" + firstBook.getId());
        assertThat(response.statusCode(), is(200));
        Book deletedBook = response.as(Book.class);
        assertThat(deletedBook.getName(), is("First book"));
        assertThat(deletedBook.getCategory(), is("First category"));
        List<Book> booksAfterDelete = bookRepository.findAll();
        assertThat(booksAfterDelete, hasSize(0));
    }
}
