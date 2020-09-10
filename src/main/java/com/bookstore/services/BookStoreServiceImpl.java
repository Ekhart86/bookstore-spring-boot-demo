package com.bookstore.services;

import com.bookstore.entity.Book;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookStoreServiceImpl implements BookStoreService {

    private final BookRepository bookRepository;

    public BookStoreServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(int bookId, Book book) {
        Book bookFromDB = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        bookFromDB.setName(book.getName());
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setCategory(book.getCategory());
        bookFromDB.setPublication(book.getPublication());
        bookFromDB.setPages(book.getPages());
        bookFromDB.setPrice(book.getPrice());
        return bookRepository.save(bookFromDB);
    }

    @Override
    public Book getBook(int bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
    }

    @Override
    public Book deleteBook(int bookId) {
        Book bookFromDB = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        bookRepository.delete(bookFromDB);
        return bookFromDB;
    }

    @Override
    public Book findByNameBook(String name) {
        return bookRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Book", "name", name));
    }

    @Override
    public boolean existBook(Book book) {
        return bookRepository.existsByName(book.getName())
                && bookRepository.existsByAuthor(book.getAuthor())
                && bookRepository.existsByPublication(book.getPublication());
    }
}
