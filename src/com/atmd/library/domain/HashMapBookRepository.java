package com.atmd.library.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapBookRepository implements BookRepository {
    private final HashMap<String,Book> library = new HashMap<>();

    /**
     * 保存一本书(save)
     */
    @Override
    public void save(Book book){
        library.put(book.getIsbn(),book);
    }

    /**
     * 根据ISBN查找一本书(Read)
     * */
    @Override
    public Book findByIsbn(String isbn){
        return library.get(isbn);
    }


    /**
     * 根据书名模糊查找书籍(Read)
     * */
    @Override
    public  List<Book> findByTitleContains(String title){
        List<Book> res = new ArrayList<>();
        for(Book book : library.values()){
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
        for(Book book : library.values()){
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
        return new ArrayList<>(library.values());
    }

    /**
     * 根据ISBN删除一本书
     * */
    @Override
    public void deleteByIsbn(String isbn){
        library.remove(isbn);
    }


}
