package com.booksearch.dto.book;

import java.util.UUID;

public record BookDto(UUID id, String title, String author) {
}
