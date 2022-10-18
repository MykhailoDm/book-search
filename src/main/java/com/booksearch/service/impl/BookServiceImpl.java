package com.booksearch.service.impl;

import com.booksearch.dto.book.BookCriteriaDto;
import com.booksearch.dto.book.BookDto;
import com.booksearch.exception.BookNotFoundException;
import com.booksearch.model.Book;
import com.booksearch.repository.BookRepository;
import com.booksearch.service.BookService;
import com.booksearch.util.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.booksearch.util.SpecificationUtil.hasAuthor;
import static com.booksearch.util.SpecificationUtil.hasTitle;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<BookDto> findBooks(int page, int size, BookCriteriaDto bookCriteriaDto) {
        log.debug("Find all book with criteria: {}", bookCriteriaDto);

        PageRequest pageRequest = PageRequest.of(page, size);
        if (bookCriteriaDto.noCriteriaProvided()) {
            return bookRepository.findAll(pageRequest)
                    .map(BookMapper::bookToBookDto);
        }

        Specification<Book> searchCriteria = getSearchCriteria(bookCriteriaDto);
        Page<Book> books = bookRepository.findAll(
                searchCriteria, pageRequest);
        return books.map(BookMapper::bookToBookDto);
    }

    @Override
    public BookDto findBookById(UUID id) {
        log.debug("Find book  with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        return BookMapper.bookToBookDto(book);
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.debug("Creating book: {}", bookDto);
        Book book = BookMapper.bookDtoToBook(bookDto);
        return BookMapper.bookToBookDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        log.debug("Updating book: {}", bookDto);
        Optional<Book> existingOrder = bookRepository.findById(bookDto.id());
        if (existingOrder.isEmpty()) {
            log.error("No book  found with id: {}", bookDto.id());
            throw new BookNotFoundException("Book does not exist with id " + bookDto.id());
        }

        Book book = BookMapper.bookDtoToBook(bookDto);
        return BookMapper.bookToBookDto(bookRepository.save(book));
    }

    @Override
    public void deleteBookById(UUID id) {
        log.debug("Delte book by id: {}", id);
        bookRepository.deleteById(id);
    }

    private Specification<Book> getSearchCriteria(BookCriteriaDto bookCriteriaDto) {
        return Specification.where(hasTitle(bookCriteriaDto.title())
                .or(hasAuthor(bookCriteriaDto.author())));
    }
}
