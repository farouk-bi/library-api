package com.devsecops.library;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/** Logique metier : catalogue en memoire, emprunt et retour. */
@Service
public class BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    public BookService() {
        books.put(1L, new Book(1L, "Clean Code", "Robert C. Martin", true));
        books.put(2L, new Book(2L, "The Phoenix Project", "Gene Kim", true));
        books.put(3L, new Book(3L, "Securite informatique", "Laurent Bloch", true));
    }

    public List<Book> findAll() {
        return books.values().stream()
                .sorted(Comparator.comparingLong(Book::id))
                .toList();
    }

    /**
     * @throws NoSuchElementException si le livre n'existe pas
     * @throws IllegalStateException  si le livre est deja emprunte
     */
    public Book borrow(long id) {
        Book book = find(id);
        if (!book.available()) {
            throw new IllegalStateException("Livre deja emprunte");
        }
        return save(book.withAvailable(false));
    }

    /**
     * @throws NoSuchElementException si le livre n'existe pas
     * @throws IllegalStateException  si le livre n'etait pas emprunte
     */
    public Book giveBack(long id) {
        Book book = find(id);
        if (book.available()) {
            throw new IllegalStateException("Livre non emprunte");
        }
        return save(book.withAvailable(true));
    }

    private Book find(long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new NoSuchElementException("Livre introuvable");
        }
        return book;
    }

    private Book save(Book book) {
        books.put(book.id(), book);
        return book;
    }
}
