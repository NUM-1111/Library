package com.atmd.library.domain.repository;
import com.atmd.library.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    // 3. 定义自定义查询方法 (无需实现！)

    /**
     * 根据书名进行模糊查询（忽略大小写）
     * Spring Data JPA会根据方法名自动生成SQL查询: SELECT b FROM Book b WHERE lower(b.title) LIKE lower(concat('%', ?1, '%'))
     */
    List<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * 根据作者进行模糊查询（忽略大小写）
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);
}
