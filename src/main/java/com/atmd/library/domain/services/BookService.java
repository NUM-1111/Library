package com.atmd.library.domain.services;

import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.repository.BookRepository;
import com.atmd.library.dto.BookRequestDTO;
import com.atmd.library.dto.BookResponseDTO;
import com.atmd.library.dto.BookUpdateDTO;
import com.atmd.library.exception.BookNotFoundException;
import com.atmd.library.exception.DuplicateIsbnException;
import com.atmd.library.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    // 不再直接new一个具体的实现，而是持有一个接口引用
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * 添加书籍的业务逻辑
     */
    public BookResponseDTO createBook(BookRequestDTO bookDTO){
        if(bookRepository.existsById(bookDTO.getIsbn())){
            throw new DuplicateIsbnException("错误 ISBN " + bookDTO.getIsbn() + " 已存在 ");
        }
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(savedBook);
    }

    /**
     * 删除书籍的业务逻辑
     */
    public void deleteBook(String isbn){
        //业务规则1:检查isbn是否存在
        if(!bookRepository.existsById(isbn)){
            throw new BookNotFoundException("错误：无法删除，未找到ISBN为 " + isbn + " 的书籍。");
        }
        //完成所有业务规则后,开始进行删除
        bookRepository.deleteById(isbn);
    }

    /**
     * 修改书籍的业务逻辑
     * @return Book,用于ui显示
     */
    public BookResponseDTO updateBook(String isbn, BookUpdateDTO bookDTO){
        //业务规则1:检查需要更新的书籍的isbn是否存在(其实在此之前会进行检查,使得用户使用更加人性化)
        Book existingBook  = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("错误：无法更新，未找到ISBN为 " + isbn + " 的书籍。"));
        // 更新字段
        if (bookDTO.getTitle() != null && !bookDTO.getTitle().isEmpty()) {
            existingBook.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getAuthor() != null && !bookDTO.getAuthor().isEmpty()) {
            existingBook.setAuthor(bookDTO.getAuthor());
        }
        if (bookDTO.getPublicationYear() != null){
            existingBook.setPublicationYear(bookDTO.getPublicationYear());
        }

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toResponseDTO(updatedBook);
    }

    /**
     * 查询,氛围isbn精查询,和title和author浅查询
     * @return Book
     */
    public List<BookResponseDTO> findAllBooks(){
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookResponseDTO> findBookByIsbn(String isbn){
        return bookRepository.findById(isbn)
                .map(bookMapper::toResponseDTO);
    }

    public List<BookResponseDTO> findBookByTitle(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> findBookByAuthor(String author){
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
