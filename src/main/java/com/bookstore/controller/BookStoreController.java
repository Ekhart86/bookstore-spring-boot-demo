package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.services.BookStoreService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLDataException;
import java.util.List;

@RestController
@RequestMapping("/library")
public class BookStoreController {

    private final BookStoreService service;

    public BookStoreController(BookStoreService service) {
        this.service = service;
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return service.getBooks();
    }

    @GetMapping("/books/resource")
    public Book getBookByName(@RequestParam("name") String name) {
        return service.findByNameBook(name);
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable("id") int id) {
        return service.getBook(id);
    }

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        return service.createBook(book);
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable("id") int id, @RequestBody Book book){
        return service.updateBook(id, book);
    }

    @DeleteMapping("/books/{id}")
    public Book deleteBook(@PathVariable("id") int id) {
        return service.deleteBook(id);
    }
}
