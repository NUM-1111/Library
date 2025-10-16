package com.atmd.library.domain.repository;

import com.atmd.library.domain.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecification {
    /**
     * 创建一个“标题包含(like)”的查询条件
     */
    public Specification<Book> titleContains(String title){
        // (root, query, cb) -> cb.like(root.get("title"), "%" + title + "%")
        // root: 代表查询的根对象(Book)
        // query: 代表顶层查询
        // cb (CriteriaBuilder): 是一个用于构建查询条件的工厂对象
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    /**
     * 创建一个“作者包含(like)”的查询条件
     */
    public Specification<Book> authorContains(String author) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
    }

    /**
     * 创建一个“出版年份等于”的查询条件
     */
    public Specification<Book> publicationYearEquals(Integer year) {
        return (root, query, cb) -> cb.equal(root.get("publicationYear"), year);
    }
}
