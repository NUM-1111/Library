package com.atmd.library.domain.repository;
import com.atmd.library.domain.model.Book;

import java.util.List;

public interface BookRepository {
    //基本功能
    void save(Book book);
    Book findByIsbn(String isbn);
    List<Book> findAll();
    List<Book> findByTitleContains(String title);
    List<Book> findByAuthorContains(String author);
    void deleteByIsbn(String isbn);
    //存储功能
    void loadFromFile(String filePath);
    void saveToFile(String filePath);
}
