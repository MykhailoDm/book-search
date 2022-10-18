package com.booksearch.service;

import com.booksearch.dto.book.BookCriteriaDto;
import com.booksearch.dto.book.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BookService {
    Page<BookDto> findBooks(int page, int size, BookCriteriaDto bookCriteriaDto);

    BookDto findBookById(UUID id);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    void deleteBookById(UUID id);
}
