package com.atmd.library.domain;
import java.util.*;

public class LibraryRepository {
    //成员
    private ArrayList<Book> library = new ArrayList<>();//这是一种方法,当然还有更高效的存储方式HashMap,这个我们后续可以重构

    /**
     * 保存一本书(save)
     */
    public void save(Book book){
        library.add(book);
    }

    /**
     * 根据ISBN查找一本书(Read)
     * */
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
    public  ArrayList<Book> findByTitle(String title){
        ArrayList<Book> res = new ArrayList<>();
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
    public  ArrayList<Book> findByAuthor(String author){
        ArrayList<Book> res = new ArrayList<>();
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
    public ArrayList<Book> findAll(){
        return new ArrayList<>(library);
    }

    /**
     * 根据ISBN删除一本书
     * */
    public void deleteByIsbn(String isbn){
        library.removeIf(book -> book.getIsbn().equals(isbn));
    }
}
