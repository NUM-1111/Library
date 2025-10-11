package com.atmd.library.domain.repository;
import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.repository.BookRepository;

import java.io.*;
import java.util.*;

public class ArrayListBookRepository implements BookRepository {
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

    @Override
    public void update(Book book) {
        System.out.println();
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

    @Override
    public void loadFromFile(String filePath){
        // 使用 try-with-resources 自动关闭流
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine())!=null){
                String[] data = line.split(",");
                if(data.length == 4){
                    Book book = new Book(data[0],data[1],data[2],data[3]);
                    this.save(book);
                }
            }
            System.out.println("从文件中加载了" + library.size() + "本书籍");
        }catch(IOException e){
            System.out.println("加载文件失败，可能文件不存在，将启动一个空图书馆。 " + e.getMessage());
        }
    }

    @Override
    public void saveToFile(String filePath){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for(Book book : library){
                String line = String.join(",",
                        book.getIsbn(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublicationYear()
                );
                writer.write(line);
                writer.newLine();
            }
            System.out.println("成功将 " + library.size() + " 本书保存到文件。");
        }catch(IOException e){
            System.out.println("保存文件失败 "+e.getMessage());
        }
    }
}
