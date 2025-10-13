package com.atmd.library.controller;

import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET /books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    // GET /books/{isbn}
    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn).orElse(null);
    }

    // POST /books
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return book;
    }

    // DELETE /books/{isbn}
    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable String isbn) throws Exception {
        bookService.deleteBook(isbn);
    }
}