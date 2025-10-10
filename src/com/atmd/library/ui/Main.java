package com.atmd.library.ui;
import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.repository.BookRepository;
import com.atmd.library.domain.repository.LinkedHashMapBookRepository;
import com.atmd.library.domain.services.BookService;
import com.atmd.library.exception.BookNotFoundException;
import com.atmd.library.exception.DuplicateIsbnException;

import java.util.ArrayList;
import  java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final String DATABASE_FILE = "src/com/atmd/library/resources/library.csv";
        Scanner scanner = new Scanner(System.in);
//        BookRepository bookRepository = new ArrayListBookRepository();//数据存储方式为动态列表
//        BookRepository bookRepository = new HashMapBookRepository();//数据存储方式为哈希表
        BookRepository bookRepository = new LinkedHashMapBookRepository();//数据存储方式为双向链表+哈希表-->解决打印乱序问题
        bookRepository.loadFromFile(DATABASE_FILE); // 程序启动时加载
        BookService bookService = new BookService(bookRepository);

        LOOP_BREAK:
        while(true){
            System.out.println("===========图书管理系统=============");
            System.out.println("1.添加书籍");
            System.out.println("2.删除书籍");
            System.out.println("3.修改书籍");
            System.out.println("4.查询单本书籍");
            System.out.println("5.打印所有书籍");
            System.out.println("6.退出");
            System.out.print("请输入: ");
            String operation = scanner.nextLine();
            switch (operation) {
                case "1":
                try{
                    System.out.print("输入该书籍的书名: ");
                    String title = scanner.nextLine();

                    System.out.print("输入该书籍的作者: ");
                    String author = scanner.nextLine();

                    System.out.print("输入该书籍的isbn: ");
                    String isbn = scanner.nextLine();

                    System.out.print("输入该书籍的出版年份: ");
                    String publicationYear = scanner.nextLine();

                    Book book = new Book();
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setIsbn(isbn);
                    book.setPublicationYear(publicationYear);

                    long startTime = System.nanoTime();
                    bookService.addBook(book);
                    long endTime = System.nanoTime();
                    long durationInNanos = endTime - startTime;

                    System.out.println("已添加如下书籍信息");
                    System.out.println("ibsn: " + isbn);
                    System.out.println("title: " + title);
                    System.out.println("author: " + author);
                    System.out.println("publicationYear: " + publicationYear);

                    System.out.println("功能1耗时: " + durationInNanos);
                }catch (RuntimeException e){
                    // 捕获异常，并优雅地展示给用户
                    System.out.println(e.getMessage());
                }
                break;

                case "2":
                try{
                    System.out.print("输入该书籍的isbn: ");
                    String isbn = scanner.nextLine();

                    long startTime = System.nanoTime();
                    bookService.deleteBook(isbn);
                    long endTime = System.nanoTime();
                    long durationInNanos = endTime - startTime;
                    System.out.println("功能2耗时: " + durationInNanos);

                }catch(BookNotFoundException e){
                    System.out.println(e.getMessage());
                }
                break;

                case "3":
                try{
                    System.out.print("输入该书籍的isbn: ");
                    String isbn = scanner.nextLine();

                    if (bookService.getBookByIsbn(isbn) == null) {
                        System.out.println("需要输入的isbn不存在!");
                        continue;
                    }

                    System.out.print("输入需要修改书名: ");
                    String title = scanner.nextLine();

                    System.out.print("输入需要修改的书籍作者: ");
                    String author = scanner.nextLine();

                    System.out.print("输入需要修改的书籍出版年份: ");
                    String publicationYear = scanner.nextLine();

                    Book book = new Book();
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setIsbn(isbn);
                    book.setPublicationYear(publicationYear);

                    long startTime = System.nanoTime();
                    bookService.updateBook(book);
                    long endTime = System.nanoTime();
                    long durationInNanos = endTime - startTime;
                    System.out.println("功能3耗时: " + durationInNanos);
                }catch (BookNotFoundException e){
                    System.out.println(e.getMessage());
                }
                break;

                case "4":
                {
                    List<Book> result = new ArrayList<>();

                    System.out.println("选择需要查询的词语");
                    System.out.println("1.isbn");
                    System.out.println("2.书名");
                    System.out.println("3.作者");
                    System.out.print("请输入: ");
                    String choice = scanner.nextLine();
                    if (choice.equals("1")) {
                        System.out.print("输入isbn: ");
                        String isbn = scanner.nextLine();
                        result.add(bookService.getBookByIsbn(isbn));
                    } else if (choice.equals("2")) {
                        System.out.print("输入书名: ");
                        String title = scanner.nextLine();
                        result = bookService.getBooksByTitle(title);
                    } else if (choice.equals("3")) {
                        System.out.print("输入作者: ");
                        String author = scanner.nextLine();
                        result = bookService.getBookByAuthor(author);
                    } else {
                        System.out.println("输入不合法");
                        continue;
                    }
                    for (Book book : result) {
                        System.out.println(book.toString());
                    }

                }
                break;

                case"5":
                {
                    long startTime = System.nanoTime();
                    List<Book> result = bookService.findAllBooks();
                    long endTime = System.nanoTime();
                    long durationInNanos = endTime - startTime;

                    for (Book book : result) {
                        System.out.println(book.toString());
                    }

                    System.out.println("遍历 耗时: " + durationInNanos);
                }
                break;

                case"6":
                {
                    bookRepository.saveToFile(DATABASE_FILE);
                    System.out.println("数据已保存，程序退出。");
                    break LOOP_BREAK;
                }

                default:
                    System.out.println("无效输入");
            }
        }
    }
}
