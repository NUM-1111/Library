package com.atmd.library.domain;

import java.util.ArrayList;

public class LibraryService {
    private LibraryRepository libraryRepository = new LibraryRepository();

    /**
     * 添加书籍的业务逻辑
     * @return true 如果添加成功, false 如果失败 (例如ISBN已存在)
     */
    public boolean addBook(Book newBook){
        //业务规则1:验证数据完整性
        if(newBook.getIsbn()==null || newBook.getTitle()==null || newBook.getIsbn().isEmpty() || newBook.getTitle().isEmpty()){
            System.out.println("错误!书籍信息不完整!");
            return false;
        }
        //业务规则2:检查isbn是否已经存在
        if(libraryRepository.findByIsbn(newBook.getIsbn())!=null){
            System.out.println("错误!isbn已存在!");
            return false;
        }
        //业务规则都通过之后,再进行Repository进行存储
        libraryRepository.save(newBook);
        System.out.println("添加 <" + newBook.getTitle() + "> 已成功!");
        return true;
    }

    /**
     * 删除书籍的业务逻辑
     * @return true 如果删除成功, false 如果失败 (例如ISBN不存在)
     */
    public boolean deleteBook(String isbn){
        //业务规则1:检查isbn是否存在
        if(libraryRepository.findByIsbn(isbn)==null){
            System.out.println("错误!需要删除书籍的isbn不存在!");
            return false;
        }
        //完成所有业务规则后,开始进行删除
        libraryRepository.deleteByIsbn(isbn);
        System.out.println("isbn编号为" + isbn + "的书籍已经删除");
        return true;
    }

    /**
     * 修改书籍的业务逻辑
     * @return Book,用于ui显示
     */
    public Book updateBook(Book updateBook){
        //业务规则1:检查需要更新的书籍的isbn是否存在(其实在此之前会进行检查,使得用户使用更加人性化)
        Book book = libraryRepository.findByIsbn(updateBook.getIsbn());
        if(book == null){
            System.out.println("错误!需要更新书籍的isbn不存在!");
            return null;
        }
        //业务逻辑2:更新必要部分
        if(updateBook.getAuthor()!=null || !updateBook.getAuthor().isEmpty()){
            book.setAuthor(updateBook.getAuthor());
        }
        if(updateBook.getTitle()!=null || !updateBook.getTitle().isEmpty()){
            book.setTitle(updateBook.getTitle());
        }
        if(updateBook.getPublicationYear()!=null || !updateBook.getPublicationYear().isEmpty()){
            book.setPublicationYear(updateBook.getPublicationYear());
        }
        //所欲业务逻辑完成后
        System.out.println("更新信息成功");
        return book;
    }

    /**
     * 查询,氛围isbn精查询,和title和author浅查询
     * @return Book
     */
    public ArrayList<Book> findAllBooks(){
        return libraryRepository.findAll();
    }

    public Book getBookByIsbn(String isbn){
        return libraryRepository.findByIsbn(isbn);
    }

    public ArrayList<Book> getBooksByTitle(String title){
        return libraryRepository.findByTitle(title);
    }

    public ArrayList<Book> getBookByAuthor(String author){
        return libraryRepository.findByAuthor(author);
    }

}
