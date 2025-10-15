package com.atmd.library.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity//1. 告诉JPA,这是一个实体类,它对应数据库中的一张表
@Table(name = "books")//2. 明确这张表的名字为books
public class Book {
    @Id//3. 告诉JPA,这个字段是主键
    @NotBlank(message = "ISBN不能为空")
    private String isbn;

    //@Column注解可以更加详细地定义列的属性,比如不允许为空
    @Column(nullable = false)
    @NotBlank(message = "书名不能为空")
    private String title;
    private String author;

    // 4. 如果字段名和数据库列名不完全一样（比如Java用驼峰，数据库用下划线），用name属性来映射
    @Column(name = "publication_year")
    private Integer publicationYear;

    // --- 构造方法和 `equals`/`hashCode`/`toString` 保持不变 ---
    public Book(String title, String author, Integer publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }

    public Book() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book otherBook = (Book) obj;
        return Objects.equals(isbn, otherBook.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + ", 书名: " + title + ", 作者: " + author + ", 出版年份: " + publicationYear;
    }

    // --- Getter 和 Setter 保持不变 ---
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }
}
