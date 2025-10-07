package com.atmd.library.domain;

public class Book {
    ///成员
    private String title;
    private String author;
    private String isbn;//唯一标识
    private String publicationYear;

    // --- 带所有参数的构造方法 ---
    public Book(String title, String author, String publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }

    // 默认的无参构造方法 (如果你需要它，通常也需要写出来)
    public Book() {

    }


        ///方法
    //各个private变量的Setter和Getter
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

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }
}
