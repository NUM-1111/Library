package com.atmd.library.domain;

import java.util.*;

public class BookOperation {
    //成员
    private ArrayList<Book> library;//这是一种方法,当然还有更高效的存储方式HashMap,这个我们后续可以重构

    public BookOperation(){
        library = new ArrayList<>();
    }


    //方法:添加书籍
    //功能:输入成员信息进行书籍的添加
    //实现:保证在存储结构里,ISBN是绝对标识
    public boolean addBook(String title,String author,String isbn,String publicationYear){
        for(Book book : library){
            if(book.getIsbn().equals(isbn)){
                System.out.println("已存在同样的isbn!无法添加!");
                return false;
            }
        }
        Book book = new Book(title, author,publicationYear, isbn);
        library.add(book);
        return true;
    }

    //方法:删除书籍
    //功能:通过isbn删除具体书籍
    public boolean delBook(String isbn){
        for(Book book : library){
            if(book.getIsbn().equals(isbn)){
                library.remove(book);
                return true;
            }
        }
        return false;
    }

    //方法:修改书籍信息
    //功能:通过查找isbn找到一本书,再更新其信息
    //实现:isbn确定后不可以随便修改,其次可以直写需要修改的部分
    public boolean updateBook(String title,String author,String isbn,String publicationYear){
        for(Book book : library){
            if(book.getIsbn().equals(isbn)){
                book.setAuthor(author);
                book.setTitle(title);
                book.setPublicationYear(publicationYear);
                return true;
            }
        }
        return false;
    }

    //方法:查询书籍信息
    //功能:依据isbn精准查找,根据书名,作者模糊查找
    public ArrayList<Book> findBook(String title,String author,String isbn){
        ArrayList<Book> res = new ArrayList<>();

        if(isbn != null){//根据isbn进行精度搜索
            for(Book book : library){
                if(book.getIsbn().equals(isbn)){
                    res.add(book);
                }
            }
        }else if(title != null){//根据书名进行粗略搜索
            for(Book book : library){
                if(book.getTitle().equals(title)){
                    res.add(book);
                }
            }
        }else if(author != null){//根据作者进行粗略锁锁
            for(Book book : library){
                if(book.getAuthor().equals(author)){
                    res.add(book);
                }
            }
        }else{
            System.out.println("该书籍不存在");
        }
        return res;
    }

    // 1. 重载方法：只按书名搜索
    public ArrayList<Book> findBook(String title) {
        // 传入 title，并将其他两个参数设置为 null
        return findBook(title, null, null);
    }

    // 2. 重载方法：只按作者搜索
    public ArrayList<Book> findBookByAuthor(String author) {
        // 为了避免和上面的 findBook(String) 冲突，我们使用一个更明确的名字
        return findBook(null, author, null);
    }

    // 3. 重载方法：只按 ISBN 搜索
    public ArrayList<Book> findBookByISBN(String isbn) {
        // 同样使用明确的名字，避免歧义
        return findBook(null, null, isbn);
    }


    //方法:打印所有书籍信息
    //功能:可展示所有书籍信息(可拓展,当书的数量过多时)
    public void listAllBooks(){
        for(Book book:library){
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("PublicationYear: " + book.getPublicationYear() + "\n");
        }
    }
}
