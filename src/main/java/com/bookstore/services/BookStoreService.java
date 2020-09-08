package com.bookstore.services;

import com.bookstore.entity.Book;

import java.util.List;

public interface BookStoreService {
    List<Book> getBooks();

    Book createBook(Book book);

    Book updateBook(int bookId, Book book);

    Book getBook(int bookId);

    Book deleteBook(int bookId);

    Book findByNameBook(String name);
}
