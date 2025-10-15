package com.atmd.library.mapper;

import com.atmd.library.domain.model.Book;
import com.atmd.library.dto.BookRequestDTO;
import com.atmd.library.dto.BookResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    //将 RequestDTO 转换为 Entity
    public Book toEntity(BookRequestDTO dto){
        Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublicationYear(dto.getPublicationYear());
        return book;
    }

    public BookResponseDTO toResponseDTO(Book book){
        BookResponseDTO dto = new BookResponseDTO();
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublicationYear(book.getPublicationYear());
        return dto;
    }
}
