package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.exception.DuplicateRecordException;
import com.bookstore.services.BookStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookStoreController {

    private final BookStoreService service;

    public BookStoreController(BookStoreService service) {
        this.service = service;
    }

    @GetMapping("/books")
    @Operation(description = "Get all books", security = @SecurityRequirement(name = "basicAuth"))
    public List<Book> getBooks() {
        return service.getBooks();
    }

    @GetMapping("/books/resource")
    @Operation(description = "Get a book by name", security = @SecurityRequirement(name = "basicAuth"))
    public Book getBookByName(@RequestParam("name") String name) {
        return service.findByNameBook(name);
    }

    @GetMapping("/books/{id}")
    @Operation(description = "Get a book by ID", security = @SecurityRequirement(name = "basicAuth"))
    public Book getBookById(@PathVariable("id") int id) {
        return service.getBook(id);
    }

    @PostMapping("/books")
    @Operation(description = "Create a new book", security = @SecurityRequirement(name = "basicAuth"))
    public Book createBook(@RequestBody Book book) {
        if (service.existBook(book)) throw new DuplicateRecordException(book);
        return service.createBook(book);
    }

    @PutMapping("/books/{id}")
    @Operation(description = "Change book details", security = @SecurityRequirement(name = "basicAuth"))
    public Book updateBook(@PathVariable("id") int id, @RequestBody Book book) {
        if (service.existBook(book)) throw new DuplicateRecordException(book);
        return service.updateBook(id, book);
    }

    @DeleteMapping("/books/{id}")
    @Operation(description = "Delete book", security = @SecurityRequirement(name = "basicAuth"))
    public Book deleteBook(@PathVariable("id") int id) {
        return service.deleteBook(id);
    }
}
