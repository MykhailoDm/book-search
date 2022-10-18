package com.booksearch.util;

import com.booksearch.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {

    // Book

    public static Specification<Book> hasTitle(String title) {
        return (book, cq, cb) -> cb.equal(book.get("title"), title);
    }

    public static Specification<Book> hasAuthor(String author) {
        return (book, cq, cb) -> cb.equal(book.get("author"), author);
    }
}
