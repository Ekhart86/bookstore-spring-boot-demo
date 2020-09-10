package com.bookstore.exception;

import com.bookstore.entity.Book;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateRecordException extends RuntimeException {

    public DuplicateRecordException(Book book) {
        super(String.format("A book titled '%s', by '%s', published by '%s' already exists.",
                book.getName(), book.getAuthor(), book.getPublication()));

    }
}
