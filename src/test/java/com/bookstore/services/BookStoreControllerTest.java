package com.bookstore.services;

import com.bookstore.config.TestConfig;
import com.bookstore.controller.BookStoreController;
import com.bookstore.entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@Import(TestConfig.class)
public class BookStoreControllerTest {

    private BookStoreController controller;

    @Mock
    private BookStoreService service;

    @Autowired
    @Qualifier(value = "firstBook")
    private Book firstBook;

    @Autowired
    @Qualifier(value = "secondBook")
    private Book secondBook;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new BookStoreController(service);
    }

    @Test
    public void getListBookTest() {
        firstBook.setId(1);
        secondBook.setId(2);
        List<Book> listBooks = new ArrayList<>(Arrays.asList(firstBook, secondBook));
        BDDMockito.given(service.getBooks()).willReturn(listBooks);
        List<Book> booksFromController = controller.getBooks();
        assertThat(booksFromController.size(), is(2));
        assertThat(booksFromController.get(0).getAuthor(), is("First author"));
        assertThat(booksFromController.get(1).getPublication(), is("Second publication"));
    }

    @Test
    public void findBookByIdTest() {
        firstBook.setId(1);
        BDDMockito.given(service.getBook(1)).willReturn(firstBook);
        Book foundById = controller.getBookById(1);
        assertThat(foundById.getName(), is("First book"));
        assertThat(foundById.getPublication(), is("First publication"));
    }

    @Test
    public void findBookByNameTest() {
        secondBook.setId(2);
        BDDMockito.given(service.findByNameBook("Second book")).willReturn(secondBook);
        Book foundById = controller.getBookByName("Second book");
        assertThat(foundById.getId(), is(2));
        assertThat(foundById.getPublication(), is("Second publication"));
    }

    @Test
    public void createNewBookTest() {
        firstBook.setId(1);
        BDDMockito.given(service.createBook(firstBook)).willReturn(firstBook);
        Book createdBook = controller.createBook(firstBook);
        assertThat(createdBook.getName(), is("First book"));
        assertThat(createdBook.getPublication(), is("First publication"));
        assertThat(createdBook.getPages(), is(100));
    }

    @Test
    public void updateBookTest() throws SQLDataException {
        secondBook.setId(2);
        BDDMockito.given(service.updateBook(2, secondBook)).willReturn(secondBook);
        Book result = controller.updateBook(2, secondBook);
        assertThat(result.getName(), is("Second book"));
        assertThat(result.getId(), is(2));
        assertThat(result.getPrice(), is(2000));
    }

    @Test
    public void deleteBookTest() {
        secondBook.setId(2);
        BDDMockito.given(service.deleteBook(2)).willReturn(secondBook);
        Book result = controller.deleteBook(2);
        assertThat(result.getName(), is("Second book"));
        assertThat(result.getPublication(), is("Second publication"));
        assertThat(result.getPages(), is(200));
    }
}
