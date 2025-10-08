package com.atmd.library.ui;
import com.atmd.library.domain.Book;
import com.atmd.library.domain.BookOperation;

import java.util.ArrayList;
import  java.util.Scanner;

public class Library {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookOperation bookOperation = new BookOperation();
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

            // 调用 addBook 方法加入图书
            bookOperation.addBook(title, author, isbn, year);

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

                long startTime = System.nanoTime();
                if(bookOperation.addBook(title,author,isbn,publicationYear)){
                    System.out.println("操作成功");
                }else{
                    System.out.println("操作失败");
                }
                long endTime = System.nanoTime();
                long durationInNanos = endTime - startTime;
                System.out.println("功能1耗时: " + durationInNanos);

            }else if(operation.equals("2")){
                System.out.print("输入该书籍的isbn: ");
                String isbn = scanner.nextLine();

                long startTime = System.nanoTime();
                if(bookOperation.delBook(isbn)){
                    System.out.println("操作成功");
                }else{
                    System.out.println("操作失败");
                }
                long endTime = System.nanoTime();
                long durationInNanos = endTime - startTime;
                System.out.println("功能2耗时: " + durationInNanos);
            }else if(operation.equals("3")){
                System.out.print("输入该书籍的isbn: ");
                String isbn = scanner.nextLine();

                System.out.print("输入需要修改书名: ");
                String title = scanner.nextLine();

                System.out.print("输入需要修改的书籍作者: ");
                String author = scanner.nextLine();

                System.out.print("输入需要修改的书籍出版年份: ");
                String publicationYear = scanner.nextLine();

                long startTime = System.nanoTime();
                if(bookOperation.updateBook(title,author,isbn,publicationYear)){
                    System.out.println("操作成功");
                }else{
                    System.out.println("操作失败");
                }
                long endTime = System.nanoTime();
                long durationInNanos = endTime - startTime;
                System.out.println("功能3耗时: " + durationInNanos);

            }else if(operation.equals("4")){
                ArrayList<Book> result = new ArrayList<>();

                System.out.println("选择需要查询的词语");
                System.out.println("1.isbn");
                System.out.println("2.书名");
                System.out.println("3.作者");
                System.out.print("请输入: ");
                String choice = scanner.nextLine();
                if(choice.equals("1")){
                    System.out.print("输入isbn: ");
                    String isbn = scanner.nextLine();
                    result = bookOperation.findBookByISBN(isbn);
                }else if(choice.equals("2")){
                    System.out.print("输入书名: ");
                    String title = scanner.nextLine();
                    result = bookOperation.findBook(title);
                }else if(choice.equals("3")){
                    System.out.print("输入作者: ");
                    String author = scanner.nextLine();
                    result = bookOperation.findBookByAuthor(author);
                }else{
                    System.out.println("输入不合法");
                    continue;
                }
                for(Book book : result){
                    System.out.println("ISBN: " + book.getIsbn());
                    System.out.println("Title: " + book.getTitle());
                    System.out.println("Author: " + book.getAuthor());
                    System.out.println("PublicationYear: " + book.getPublicationYear() + "\n");
                }
            }else if(operation.equals("5")){
                long startTime = System.nanoTime();
                bookOperation.listAllBooks();
                long endTime = System.nanoTime();
                long durationInNanos = endTime - startTime;
                System.out.println("遍历 耗时: " + durationInNanos);
            }else if(operation.equals("6")){
                break;
            }else{
                System.out.println("输入不合法!请重新输入");
            }
        }
    }
}
