package com.atmd.library.controller;

import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    // PUT /books/{isbn}
    @PutMapping("/{isbn}")
    public Book updateBook(@PathVariable String isbn, @RequestBody Book bookDetails) throws Exception{
        // 确保URL中的isbn和请求体中的isbn一致，或以URL为准
        bookDetails.setIsbn(isbn);
        return bookService.updateBook(bookDetails);
    }


// GET /books/search?title=some_title
// 或
// GET /books/search?author=some_author
    @GetMapping("/search")
    public List<Book> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author) {

        // 根据传入的参数决定调用哪个服务
        if (title != null) {
            return bookService.getBooksByTitle(title);
        } else if (author != null) {
            return bookService.getBookByAuthor(author);
        }
        // 如果没有提供任何参数，可以返回一个空列表或抛出异常
        return new ArrayList<>();
    }

}