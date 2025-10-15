package com.atmd.library.controller;

import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.services.BookService;
import com.atmd.library.dto.BookRequestDTO;
import com.atmd.library.dto.BookResponseDTO;
import com.atmd.library.dto.BookUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<BookResponseDTO> getAllBooks() {
        return bookService.findAllBooks();
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
    public List<BookResponseDTO> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author) {

        // 根据传入的参数决定调用哪个服务
        if (title != null) {
            return bookService.findBookByTitle(title);
        } else if (author != null) {
            return bookService.findBookByAuthor(author);
        }
        // 如果没有提供任何参数，可以返回一个空列表或抛出异常
        return new ArrayList<>();
    }

}