package com.atmd.library.ui;
import com.atmd.library.domain.*;

import java.util.ArrayList;
import  java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        BookRepository bookRepository = new ArrayListBookRepository();
        BookRepository bookRepository = new HashMapBookRepository();
        BookService bookService = new BookService(bookRepository);
        for (int i = 1; i <= 30; i++) {
            // 动态生成书名，例如："测试书籍 No.1"
            String title = "测试书籍 No." + i;

            // 动态生成作者名，例如："测试作者" (保持作者名不变，或修改为 "测试作者 No." + i)
            String author = "测试作者";

            // 动态生成唯一的 ISBN，使用 String.format 保证编号格式一致，例如："B001", "B010", "B050"
            String isbn = String.format("B%03d", i);

            // 动态生成年份，例如："2023"
            // 这里我简单地使用当前年份 (2024) 减去 i，或者直接用固定年份
            String year = String.valueOf(2024 - (i % 20)); // 例如 2024, 2023... 2005

            //把信息用Book存储
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setPublicationYear(year);

            // 调用 addBook 方法加入图书
            bookService.addBook(book);

            // [可选] 打印一下，确认加入了哪本书
            System.out.println("已添加: " + title + " (ISBN: " + isbn + ")");
        }
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
            if(operation.equals("1")){
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

            }else if(operation.equals("2")){
                System.out.print("输入该书籍的isbn: ");
                String isbn = scanner.nextLine();

                long startTime = System.nanoTime();
                bookService.deleteBook(isbn);
                long endTime = System.nanoTime();
                long durationInNanos = endTime - startTime;
                System.out.println("功能2耗时: " + durationInNanos);

            }else if(operation.equals("3")){
                System.out.print("输入该书籍的isbn: ");
                String isbn = scanner.nextLine();

                if(bookService.getBookByIsbn(isbn)==null){
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

            }else if(operation.equals("4")){
                List<Book> result = new ArrayList<>();

                System.out.println("选择需要查询的词语");
                System.out.println("1.isbn");
                System.out.println("2.书名");
                System.out.println("3.作者");
                System.out.print("请输入: ");
                String choice = scanner.nextLine();
                if(choice.equals("1")){
                    System.out.print("输入isbn: ");
                    String isbn = scanner.nextLine();
                    result.add(bookService.getBookByIsbn(isbn));
                }else if(choice.equals("2")){
                    System.out.print("输入书名: ");
                    String title = scanner.nextLine();
                    result = bookService.getBooksByTitle(title);
                }else if(choice.equals("3")){
                    System.out.print("输入作者: ");
                    String author = scanner.nextLine();
                    result = bookService.getBookByAuthor(author);
                }else{
                    System.out.println("输入不合法");
                    continue;
                }
                for(Book book : result){
                    System.out.println(book.toString());
                }

            }else if(operation.equals("5")){
                long startTime = System.nanoTime();
                List<Book> result = bookService.findAllBooks();
                long endTime = System.nanoTime();
                long durationInNanos = endTime - startTime;

                for(Book book : result){
                    System.out.println(book.toString());
                }

                System.out.println("遍历 耗时: " + durationInNanos);
            }else if(operation.equals("6")){
                break;
            }else{
                System.out.println("输入不合法!请重新输入");
            }
        }
    }
}
