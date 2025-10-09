package com.atmd.library.domain;
import java.util.*;

public class ArrayListBookRepository implements BookRepository{
    //成员
    private final ArrayList<Book> library = new ArrayList<>();//这是一种方法,当然还有更高效的存储方式HashMap,这个我们后续可以重构

    /**
     * 保存一本书(save)
     */
    @Override
    public void save(Book book){
        library.add(book);
    }

    /**
     * 根据ISBN查找一本书(Read)
     * */
    @Override
    public Book findByIsbn(String isbn){
        for(Book book : library){
            if(book.getIsbn().equals(isbn)){
                return book;
            }
        }
        return null;
    }

    /**
     * 根据书名模糊查找书籍(Read)
     * */
    @Override
    public  List<Book> findByTitleContains(String title){
        List<Book> res = new ArrayList<>();
        for(Book book : library){
            if(book.getTitle().toLowerCase().contains(title.toLowerCase())){
                res.add(book);
            }
        }
        return res;
    }

    /**
     * 根据作者模糊查找书籍(Read)
     * */
    @Override
    public  List<Book> findByAuthorContains(String author){
        List<Book> res = new ArrayList<>();
        for(Book book : library){
            if(book.getAuthor().toLowerCase().contains(author.toLowerCase())){
                res.add(book);
            }
        }
        return res;
    }

    /**
     * 查找所有的book(Read)
     * */
    @Override
    public List<Book> findAll(){
        return new ArrayList<>(library);
    }

    /**
     * 根据ISBN删除一本书
     * */
    @Override
    public void deleteByIsbn(String isbn){
        library.removeIf(book -> book.getIsbn().equals(isbn));
    }
}
