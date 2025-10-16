package com.atmd.library.controller;

import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.services.BookService;
import com.atmd.library.dto.BookRequestDTO;
import com.atmd.library.dto.BookResponseDTO;
import com.atmd.library.dto.BookUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
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
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        /*当您在方法参数中声明了一个Pageable pageable时，Spring Boot会自动启用一个强大的功能：
        它会检查HTTP请求的URL中是否包含page, size, sort这几个参数，并自动将它们的值组装成一个Pageable对象，传递给您的方法。*/
        return bookService.findAllBooks(pageable);
    }

    // GET /books/{isbn}
    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponseDTO> getBookByIsbn(@PathVariable String isbn) {
        return bookService.findBookByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /books
    @PostMapping
    public BookResponseDTO addBook(@Valid @RequestBody  BookRequestDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }

    // DELETE /books/{isbn}
    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable String isbn){
        bookService.deleteBook(isbn);
    }

    // PUT /books/{isbn}
    @PutMapping("/{isbn}")
    public BookResponseDTO updateBook(@PathVariable String isbn,@Valid @RequestBody BookUpdateDTO bookDTO){
        return bookService.updateBook(isbn,bookDTO);
    }


// GET /books/search?title=some_title
// 或
// GET /books/search?author=some_author
@GetMapping("/search")
public Page<BookResponseDTO> searchBooks(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) Integer year,
        Pageable pageable) { // 同时也支持分页和排序！

    return bookService.searchBooks(title, author, year, pageable);
}

}