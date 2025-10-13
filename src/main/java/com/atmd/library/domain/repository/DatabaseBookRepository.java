// src/main/java/com/atmd/library/domain/repository/DatabaseBookRepository.java

package com.atmd.library.domain.repository;

import com.atmd.library.domain.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DatabaseBookRepository implements BookRepository {

    // 1. 声明 JdbcTemplate
    private final JdbcTemplate jdbcTemplate;

    // 2. 通过构造函数注入 JdbcTemplate
    // Spring会自动创建JdbcTemplate并把它传递进来
    @Autowired
    public DatabaseBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 3. 定义一个 RowMapper，它知道如何将数据库的一行记录映射成一个 Book 对象
    // 这能消除大量重复代码
    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> new Book(
            rs.getString("title"),
            rs.getString("author"),
            String.valueOf(rs.getInt("publication_year")),
            rs.getString("isbn")
    );

    @Override
    public void save(Book book) {
        String sql = "INSERT INTO books(isbn, title, author, publication_year) VALUES(?, ?, ?, ?)";
        // 使用 jdbcTemplate.update() 执行插入、更新、删除操作
        jdbcTemplate.update(sql,
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                Integer.parseInt(book.getPublicationYear())
        );
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try {
            // 使用 jdbcTemplate.queryForObject() 查询单个对象
            // 第三个参数就是我们定义的 bookRowMapper，它负责转换结果
            Book book = jdbcTemplate.queryForObject(sql, bookRowMapper, isbn);
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            // 如果 queryForObject 找不到任何记录，会抛出这个异常
            return Optional.empty();
        }
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE isbn = ?";
        jdbcTemplate.update(sql,
                book.getTitle(),
                book.getAuthor(),
                Integer.parseInt(book.getPublicationYear()),
                book.getIsbn()
        );
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        // 使用 jdbcTemplate.query() 查询一个列表
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        String sql = "DELETE FROM books WHERE isbn = ?";
        jdbcTemplate.update(sql, isbn);
    }

    @Override
    public List<Book> findByTitleContains(String title) {
        String sql = "SELECT * FROM books WHERE title ILIKE ?";
        return jdbcTemplate.query(sql, bookRowMapper, "%" + title + "%");
    }

    @Override
    public List<Book> findByAuthorContains(String author) {
        String sql = "SELECT * FROM books WHERE author ILIKE ?";
        return jdbcTemplate.query(sql, bookRowMapper, "%" + author + "%");
    }

    // ... 文件操作方法保持不变 ...
    @Override
    public void loadFromFile(String filePath) {
        // ...
    }

    @Override
    public void saveToFile(String filePath) {
        // ...
    }
}