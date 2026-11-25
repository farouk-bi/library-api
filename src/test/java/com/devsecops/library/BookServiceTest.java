package com.devsecops.library;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookServiceTest {

    private final BookService service = new BookService();

    @Test
    void catalogueStartsWithThreeAvailableBooks() {
        assertThat(service.findAll()).hasSize(3).allMatch(Book::available);
    }

    @Test
    void borrowThenReturn() {
        assertThat(service.borrow(1L).available()).isFalse();
        assertThat(service.giveBack(1L).available()).isTrue();
    }

    @Test
    void borrowTwiceIsRejected() {
        service.borrow(2L);
        assertThatThrownBy(() -> service.borrow(2L)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void returnBookNotBorrowedIsRejected() {
        assertThatThrownBy(() -> service.giveBack(3L)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void unknownBookIsRejected() {
        assertThatThrownBy(() -> service.borrow(99L)).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> service.giveBack(99L)).isInstanceOf(NoSuchElementException.class);
    }
}
