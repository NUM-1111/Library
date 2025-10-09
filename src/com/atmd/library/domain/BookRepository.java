package com.atmd.library.domain;
import java.util.List;

public interface BookRepository {
    void save(Book book);
    Book findByIsbn(String isbn);
    List<Book> findAll();
    List<Book> findByTitleContains(String title);
    List<Book> findByAuthorContains(String author);
    void deleteByIsbn(String isbn);
}
