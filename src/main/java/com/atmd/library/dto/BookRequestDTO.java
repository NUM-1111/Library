package com.atmd.library.dto;

import javax.validation.constraints.NotBlank;

// 这个DTO专门用于接收创建或更新Book的请求
public class BookRequestDTO {

    @NotBlank(message = "ISBN不能为空")
    private String isbn;

    @NotBlank(message = "书名不能为空")
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
