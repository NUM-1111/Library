package com.atmd.library.ui;
import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.repository.BookRepository;
import com.atmd.library.domain.repository.DatabaseBookRepository;
import com.atmd.library.domain.repository.LinkedHashMapBookRepository;
import com.atmd.library.domain.services.BookService;
import com.atmd.library.exception.BookNotFoundException;
import com.atmd.library.exception.DuplicateIsbnException;

import java.util.ArrayList;
import java.util.Optional;
import  java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        final String DATABASE_FILE = "src/com/atmd/library/resources/library.csv";
        Scanner scanner = new Scanner(System.in);
//        BookRepository bookRepository = new ArrayListBookRepository();//数据存储方式为动态列表
//        BookRepository bookRepository = new LinkedHashMapBookRepository();//数据存储方式为双向链表+哈希表-->解决打印乱序问题
        BookRepository bookRepository = new DatabaseBookRepository();//数据库存储
//        bookRepository.loadFromFile(DATABASE_FILE); // 程序启动时加载
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
                    System.out.println("isbn编号为" + isbn + "的书籍已经删除");
                    System.out.println("功能2耗时: " + durationInNanos);

                }catch(BookNotFoundException e){
                    System.out.println(e.getMessage());
                }
                break;

                case "3":
                try{
                    System.out.print("输入该书籍的isbn: ");
                    String isbn = scanner.nextLine();

                    Optional<Book> bookOptional = bookService.getBookByIsbn(isbn);

                    if (bookOptional.isPresent()) {
                        // --- 值存在时执行 ---
                        Book existingBook = bookOptional.get();

                        // ... (提示用户输入、创建 updatedInfo 对象的代码不变) ...
                        System.out.println("找到了书籍，请输入新信息 (不想修改请直接回车):");
                        System.out.print("输入需要修改的书名: ");
                        String title = scanner.nextLine();
                        System.out.print("输入需要修改的书籍作者: ");
                        String author = scanner.nextLine();
                        System.out.print("输入需要修改的书籍出版年份: ");
                        String publicationYear = scanner.nextLine();

                        Book updatedInfo = new Book();
                        updatedInfo.setIsbn(existingBook.getIsbn());
                        updatedInfo.setTitle(title);
                        updatedInfo.setAuthor(author);
                        updatedInfo.setPublicationYear(publicationYear);

                        // 因为我们在一个正常的 if 代码块中，所以可以自由地调用会抛出检查型异常的方法
                        // 异常会被外层的 try-catch 捕获
                        Book updateBook = bookService.updateBook(updatedInfo);
                        System.out.println("更新的书籍信息为: " + updateBook);

                    } else {
                        // --- 值不存在时执行 ---
                        System.out.println("错误：未找到ISBN为 " + isbn + " 的书籍，无法修改。");
                    }

                } catch (Exception e) { // 外层的 catch 会捕获所有异常，包括 BookNotFoundException
                    System.out.println("操作失败: " + e.getMessage());
                }
                    break;

                case "4":
                {
                    final List<Book> result = new ArrayList<>();

                    System.out.println("选择需要查询的词语");
                    System.out.println("1.isbn");
                    System.out.println("2.书名");
                    System.out.println("3.作者");
                    System.out.print("请输入: ");
                    String choice = scanner.nextLine();
                    if (choice.equals("1")) {
                        System.out.print("输入isbn: ");
                        String isbn = scanner.nextLine();
                        bookService.getBookByIsbn(isbn)
                                .ifPresent(book -> result.add(book));

                    } else if (choice.equals("2")) {
                        System.out.print("输入书名: ");
                        String title = scanner.nextLine();
                        result.addAll(bookService.getBooksByTitle(title));
                    } else if (choice.equals("3")) {
                        System.out.print("输入作者: ");
                        String author = scanner.nextLine();
                        result.addAll(bookService.getBookByAuthor(author));
                    } else {
                        System.out.println("输入不合法");
                        continue;
                    }
                    for (Book book : result) {
                        System.out.println(book.toString());
                    }
                    // 如果想提示用户，可以检查集合是否为空
                    if (result.isEmpty()) {
                        System.out.println("未找到相关书籍。");
                    }

                }
                break;

                case"5":
                {
                    long startTime = System.nanoTime();
                    List<Book> result = bookService.findAllBooks();
                    long endTime = System.nanoTime();
                    long durationInNanos = endTime - startTime;

                    result.forEach(book -> System.out.println(book));

                    System.out.println("遍历 耗时: " + durationInNanos);
                }
                break;

                case"6":
                {
//                    bookRepository.saveToFile(DATABASE_FILE);
                    System.out.println("数据已保存，程序退出。");
                    break LOOP_BREAK;
                }

                default:
                    System.out.println("无效输入");
            }
        }
    }
}
