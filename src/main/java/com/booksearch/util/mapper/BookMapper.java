package com.booksearch.util.mapper;

import com.booksearch.dto.book.BookDto;
import com.booksearch.model.Book;

public class BookMapper {

    public static BookDto bookToBookDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor());
    }

    public static Book bookDtoToBook(BookDto bookDto) {
        return new Book(bookDto.id(), bookDto.title(), bookDto.author());
    }
}
