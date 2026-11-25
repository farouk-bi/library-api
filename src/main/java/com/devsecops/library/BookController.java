package com.devsecops.library;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * API REST :
 *   GET  /api/books              catalogue
 *   POST /api/books/{id}/borrow  emprunter
 *   POST /api/books/{id}/return  rendre
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> list() {
        return service.findAll();
    }

    @PostMapping("/{id}/borrow")
    public Book borrow(@PathVariable("id") long id) {
        return handle(() -> service.borrow(id));
    }

    @PostMapping("/{id}/return")
    public Book giveBack(@PathVariable("id") long id) {
        return handle(() -> service.giveBack(id));
    }

    /** Traduit les erreurs metier en codes HTTP (jamais de stack trace renvoyee). */
    private Book handle(Supplier<Book> action) {
        try {
            return action.get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
