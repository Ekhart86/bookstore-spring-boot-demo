package com.bookstore.repository;

import com.bookstore.config.TestConfig;
import com.bookstore.entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource("/test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    @Qualifier(value = "firstBook")
    private Book firstBook;

    @Autowired
    @Qualifier(value = "secondBook")
    private Book secondBook;

    @Before
    public void setUp() {
        testEntityManager.persist(firstBook);
    }

    @Test
    public void findBookByIdIntegrationTest() {
        Book foundBook = bookRepository.findByName("First book").orElseThrow(NoSuchElementException::new);
        System.out.println(foundBook.toString());
        assertThat(foundBook.getCategory(), is("First category"));
        assertThat(foundBook.getPublication(), is("First publication"));
        assertThat(foundBook.getPrice(), is(1000));
    }

    @Test
    public void createNewBookIntegrationTest() {
        bookRepository.save(secondBook);
        testEntityManager.flush();
        testEntityManager.clear();
        Book savedBook = bookRepository.findByName("Second book").orElseThrow(NoSuchElementException::new);
        System.out.println(savedBook.toString());
        assertThat(savedBook.getCategory(), is("Second category"));
        assertThat(savedBook.getPublication(), is("Second publication"));
        assertThat(savedBook.getPrice(), is(2000));
    }

    @Test
    public void deleteBookByIdIntegrationTest() {
        assertThat(bookRepository.findAll().size(), is(1));
        bookRepository.deleteById(firstBook.getId());
        testEntityManager.flush();
        testEntityManager.clear();
        assertThat(bookRepository.findAll().size(), is(0));
    }

    @Test
    public void updateBookIntegrationTest() {
        firstBook.setName("Modified first book");
        firstBook.setAuthor("Modified first author");
        bookRepository.save(firstBook);
        testEntityManager.flush();
        testEntityManager.clear();
        Book updatedBook = bookRepository.findByName("Modified first book").orElseThrow(NoSuchElementException::new);
        assertThat(updatedBook.getAuthor(), is("Modified first author"));
    }
}
