package com.atmd.library.dto;

// 这个DTO专门用于向客户端返回Book的数据，我们可以精确控制返回哪些字段
public class BookResponseDTO {
    private String isbn;
    private String title;
    private String author;
    private Integer publicationYear;

    // --- Getters and Setters ---
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
}
