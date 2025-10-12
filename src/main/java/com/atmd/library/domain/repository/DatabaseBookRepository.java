package com.atmd.library.domain.repository;

import com.atmd.library.domain.model.Book;
import com.atmd.library.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseBookRepository implements BookRepository {
    @Override
    public void save(Book book){
        //SQL语句使用?作为占位符,防止SQL注入
        String sql = "INSERT INTO books(isbn,title,author, publication_year) VALUES(?, ?, ?, ?)";
        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3,book.getAuthor());
            //数据库是INT,所以需要转换
            pstmt.setInt(4,Integer.parseInt(book.getPublicationYear()));
            pstmt.executeUpdate();
        }catch (SQLException e){
            //真实项目中,会用日志框架记录错误
            throw new RuntimeException("数据库保存失败", e); // 向上抛出异常
        }catch (NumberFormatException e){
            throw new RuntimeException("年份格式错误,无法转换为数字: "+book.getPublicationYear(),e);
        }
    }

    @Override
    public Optional<Book> findByIsbn(String isbn){
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,isbn);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        String.valueOf(rs.getInt("publication_year")),
                        rs.getString("isbn")
                );
                return Optional.of(book);
            }
        }catch (SQLException e){
            System.err.println("数据库查询失败: " + e.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public void update(Book book){
        String sql = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE isbn = ?";
        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, Integer.parseInt(book.getPublicationYear()));
            pstmt.setString(4, book.getIsbn()); // WHERE 条件

            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("数据库更新失败。", e);
        }catch (NumberFormatException e){
            throw new RuntimeException("年份格式错误，无法更新。", e);
        }
    }

    @Override
    public List<Book> findAll(){
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()){

            return resultSetToSream(rs).collect(Collectors.toList());
        }catch (SQLException e){
            System.err.println("查询所有数据失败: " + e.getMessage());
        }
        return books;
    }

    @Override
    public void deleteByIsbn(String isbn){
        String sql = "DELETE FROM books WHERE isbn = ?";
        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,isbn);
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("删除书籍失败: " + e.getMessage());
            throw new RuntimeException("数据库操作失败", e); // 向上抛出异常
        }
    }

    @Override
    public List<Book> findByTitleContains(String title){
        List<Book> books = new ArrayList<>();
        // 使用ILIKE进行不区分大小写的模糊查询 (PostgreSQL特有)
        String sql = "SELECT * FROM books WHERE title ILIKE ?";
        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, "%" + title +"%");
            ResultSet rs = pstmt.executeQuery();

            return resultSetToSream(rs).collect(Collectors.toList());
        }catch (SQLException e){
            System.err.println("按照书名查询失败: " + e.getMessage());
        }
        return books;
    }

    @Override
    public List<Book> findByAuthorContains(String author) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author ILIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + author + "%");
            ResultSet rs = pstmt.executeQuery();
            return resultSetToSream(rs).collect(Collectors.toList());
        } catch (SQLException e) {
            System.err.println("按作者查询失败: " + e.getMessage());
        }
        return books;
    }

    //辅助方法1:将 ResultSet 的一行映射为一个Book对象
    private Book mapRowToBook(ResultSet rs) throws SQLException{
        return new Book(
                rs.getString("title"),
                rs.getString("author"),
                String.valueOf(rs.getInt("publication_year")),
                rs.getString("isbn")
        );
    }

    //辅助方法2:将整个ResultSet转换为一个Stream<Book>
    private Stream<Book> resultSetToSream(ResultSet rs) throws SQLException{
        List<Book> books = new ArrayList<>();
        while(rs.next()){
            books.add(mapRowToBook(rs));
        }
        return books.stream();
    }

    // 文件操作方法在这个实现中不再需要，可以留空或抛出不支持的操作异常
    @Override
    public void loadFromFile(String filePath) {
        System.out.println("Database repository does not support loading from file.");
    }

    @Override
    public void saveToFile(String filePath) {
        System.out.println("Database repository does not support saving to file.");
    }
}
