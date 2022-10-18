package com.booksearch.dto.book;

import java.util.Objects;

public record BookCriteriaDto(String title, String author) {

    public boolean noCriteriaProvided() {
        return Objects.isNull(title) && Objects.isNull(author);
    }
}
