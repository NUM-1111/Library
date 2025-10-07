package com.atmd.library.ui;
import com.atmd.library.domain.Book;
import com.atmd.library.domain.BookOperation;

import java.util.ArrayList;
import  java.util.Scanner;

public class Library {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookOperation bookOperation = new BookOperation();
        bookOperation.addBook("老人与海","海明威","001","2012");
        bookOperation.addBook("百年孤独","马尔克斯","002","2008");
        bookOperation.addBook("基督山伯爵","大仲马","003","2002");
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

                if(bookOperation.addBook(title,author,isbn,publicationYear)){
                    System.out.println("操作成功");
                }else{
                    System.out.println("操作失败");
                }
            }else if(operation.equals("2")){
                System.out.print("输入该书籍的isbn: ");
                String isbn = scanner.nextLine();
                if(bookOperation.delBook(isbn)){
                    System.out.println("操作成功");
                }else{
                    System.out.println("操作失败");
                }
            }else if(operation.equals("3")){
                System.out.print("输入该书籍的isbn: ");
                String isbn = scanner.nextLine();

                System.out.print("输入需要修改书名: ");
                String title = scanner.nextLine();

                System.out.print("输入需要修改的书籍作者: ");
                String author = scanner.nextLine();

                System.out.print("输入需要修改的书籍出版年份: ");
                String publicationYear = scanner.nextLine();
                if(bookOperation.updateBook(title,author,isbn,publicationYear)){
                    System.out.println("操作成功");
                }else{
                    System.out.println("操作失败");
                }
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
                bookOperation.listAllBooks();
            }else if(operation.equals("6")){
                break;
            }else{
                System.out.println("输入不合法!请重新输入");
            }
        }
    }
}
