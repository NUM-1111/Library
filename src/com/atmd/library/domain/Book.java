package com.atmd.library.domain;

import java.util.Objects;

public class Book {
    ///成员
    private String title;
    private String author;
    private String isbn;//唯一标识
    private String publicationYear;

    // --- 带所有参数的构造方法 ---
    public Book(String title, String author, String publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }

    // 默认的无参构造方法 (如果你需要它，通常也需要写出来)
    public Book() {

    }

    //重写方法
    @Override
    public boolean equals(Object obj){
        // 步骤1：检查是否是同一个对象引用 ("快捷方式")
        // 如果内存地址相同，那肯定是同一个对象。
        if(this == obj){
            return true;
        }

        // 步骤2：检查对象是否为null，以及是否是同一个类型
        // 如果对方是null，或者类型都不是Book，那肯定不相等
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }

        // 步骤3：类型转换
        // 经过了上面的检查，现在可以安全地将obj转为Book类型了。
        Book otherBook = (Book) obj;

        // 步骤4：核心业务逻辑比较
        // 比较两个Book对象的ISBN是否相等。
        // 使用 Objects.equals() 是最安全的方式，因为它可以正确处理isbn为null的情况。
        return Objects.equals(this.isbn, otherBook.isbn);
    }

    @Override
    public int hashCode(){
        // 使用 Objects.hash() 是自Java 7以来最简单、最推荐的方式。
        // 它会根据你传入的字段生成一个合适的哈希码，并且能优雅地处理null值。
        // 因为我们的equals只依赖isbn，所以这里也只传入isbn。
        return Objects.hash(isbn);
    }


    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear='" + publicationYear + '\'' +
                '}';
    }

    ///方法
    //各个private变量的Setter和Getter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }
}
