package com.booksearch.controller;

import com.booksearch.dto.book.BookCriteriaDto;
import com.booksearch.dto.book.BookDto;
import com.booksearch.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@Slf4j
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/query")
    public Page<BookDto> getBooks(
            @RequestBody BookCriteriaDto bookCriteriaDto,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        log.info("Get all books");
        return bookService.findBooks(page, size, bookCriteriaDto);
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable UUID id) {
        log.info("Get book by id");
        return bookService.findBookById(id);
    }

    @PostMapping
    public BookDto createBook(@RequestBody BookDto bookDto) {
        log.info("Create book");
        return bookService.createBook(bookDto);
    }

    @PutMapping
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        log.info("Update book");
        return bookService.updateBook(bookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable UUID id) {
        log.info("Delete book by id");
        bookService.deleteBookById(id);
    }
}
