package com.bookstore.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@Table(name = "BOOK")
public class Book {

    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publication", nullable = false)
    private String publication;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "pages", nullable = false)
    private int pages;

    @Column(name = "price", nullable = false)
    private int price;

    public static BookBuilder builder() {
        return new BookBuilder();
    }

    public static class BookBuilder {
        private Integer id;
        private String name;
        private String author;
        private String publication;
        private String category;
        private int pages;
        private int price;

        public BookBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public BookBuilder setName(@NotNull String name) {
            this.name = name;
            return this;
        }

        public BookBuilder setAuthor(@NotNull String author) {
            this.author = author;
            return this;
        }

        public BookBuilder setPublication(@NotNull String publication) {
            this.publication = publication;
            return this;
        }

        public BookBuilder setCategory(@NotNull String category) {
            this.category = category;
            return this;
        }

        public BookBuilder setPages(@NotNull int pages) {
            this.pages = pages;
            return this;
        }

        public BookBuilder setPrice(@NotNull int price) {
            this.price = price;
            return this;
        }

        public Book build() {
            return new Book(id, name, author, publication, category, pages, price);
        }
    }
}
