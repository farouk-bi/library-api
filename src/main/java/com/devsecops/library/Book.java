package com.devsecops.library;

/** Un livre. available = false quand il est emprunte. */
public record Book(long id, String title, String author, boolean available) {

    Book withAvailable(boolean newAvailable) {
        return new Book(id, title, author, newAvailable);
    }
}
