package com.atmd.library.domain.services;

import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.repository.BookRepository;
import com.atmd.library.exception.BookNotFoundException;
import com.atmd.library.exception.DuplicateIsbnException;

import java.util.List;

public class BookService {
    // 不再直接new一个具体的实现，而是持有一个接口引用
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    /**
     * 添加书籍的业务逻辑
     * @return true 如果添加成功, false 如果失败 (例如ISBN已存在)
     */
    public boolean addBook(Book newBook) throws RuntimeException{
        //业务规则1:验证数据完整性
        if(newBook.getIsbn()==null || newBook.getTitle()==null || newBook.getIsbn().isEmpty() || newBook.getTitle().isEmpty()){
            throw new IllegalArgumentException("错误：书籍的 ISBN 和 书名 不能为空或null。");
        }
        //业务规则2:检查isbn是否已经存在
        if(bookRepository.findByIsbn(newBook.getIsbn())!=null){
            throw new DuplicateIsbnException("错误：ISBN " + newBook.getIsbn() + " 已存在。");
        }
        //业务规则都通过之后,再进行Repository进行存储
        bookRepository.save(newBook);
        return true;
    }

    /**
     * 删除书籍的业务逻辑
     * @return true 如果删除成功, false 如果失败 (例如ISBN不存在)
     */
    public boolean deleteBook(String isbn) throws BookNotFoundException{
        //业务规则1:检查isbn是否存在
        if(bookRepository.findByIsbn(isbn)==null){
            throw new BookNotFoundException("错误：无法删除，未找到ISBN为 " + isbn + " 的书籍。");
        }
        //完成所有业务规则后,开始进行删除
        bookRepository.deleteByIsbn(isbn);
        return true;
    }

    /**
     * 修改书籍的业务逻辑
     * @return Book,用于ui显示
     */
    public Book updateBook(Book updateBook) throws BookNotFoundException{
        //业务规则1:检查需要更新的书籍的isbn是否存在(其实在此之前会进行检查,使得用户使用更加人性化)
        Book bookToUpdate  = bookRepository.findByIsbn(updateBook.getIsbn());
        if(bookToUpdate  == null){
            throw new BookNotFoundException("错误：无法更新，未找到ISBN为 " + updateBook.getIsbn() + " 的书籍。");
        }
        // 业务逻辑2: 使用传入的数据更新从数据库查找到的对象
        // 修正：判断逻辑可以更严谨，只有在传入的字符串非空时才更新
        if (updateBook.getAuthor() != null && !updateBook.getAuthor().isEmpty()) {
            bookToUpdate.setAuthor(updateBook.getAuthor());
        }
        if (updateBook.getTitle() != null && !updateBook.getTitle().isEmpty()) {
            bookToUpdate.setTitle(updateBook.getTitle());
        }
        if (updateBook.getPublicationYear() != null && !updateBook.getPublicationYear().isEmpty()) {
            bookToUpdate.setPublicationYear(updateBook.getPublicationYear());
        }

        //关键:更新到数据库里
        bookRepository.update(bookToUpdate);

        //所欲业务逻辑完成后
        return bookToUpdate;
    }

    /**
     * 查询,氛围isbn精查询,和title和author浅查询
     * @return Book
     */
    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn);
    }

    public List<Book> getBooksByTitle(String title){
        return bookRepository.findByTitleContains(title);
    }

    public List<Book> getBookByAuthor(String author){
        return bookRepository.findByAuthorContains(author);
    }

}
