package com.atmd.library.domain.services;

import com.atmd.library.domain.model.Book;
import com.atmd.library.domain.repository.BookRepository;
import com.atmd.library.exception.BookNotFoundException;
import com.atmd.library.exception.DuplicateIsbnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    //声明要测试的目标对象和它所依赖的模拟对象
    private BookService bookService;
    private BookRepository mockBookRepository;

    // @BeforeEach: 这个注解表示，在执行每一个 @Test 方法之前，都要先运行一次这个 setup 方法。
    // 这确保了每个测试都是在干净、独立的环境下进行的。
    @BeforeEach
    void setUp(){
        //1. 创建一个 BookRepository 接口的模拟对象
        mockBookRepository = Mockito.mock(BookRepository.class);
        //2. 创建 BookService 实例，但这次注入的是我们的模拟对象
        bookService = new BookService(mockBookRepository);
    }

    // @Test: 这是一个测试方法的标记。JUnit会自动运行所有带此标记的方法。
    @Test
    void addBook_shouldSaveBook_whenIsbnDoesNotExist(){
        // --- 1. 准备阶段 (Arrange) ---
        // 创建一本新书
        Book newBook = new Book("老人与海", "海明威", "1952", "B001");

        // 定义模拟对象的行为：
        // 当 mockBookRepository 的 findByIsbn 方法被以 "B001" 为参数调用时，
        // 那么 (then) 就返回一个空的 Optional (假装数据库里没有这本书)。
        when(mockBookRepository.findByIsbn("B001")).thenReturn(Optional.empty());

        // --- 2. 行动阶段 (Act) ---
        // 调用我们真正要测试的方法
        bookService.addBook(newBook);

        // --- 3. 断言阶段 (Assert) ---
        // 验证 mockBookRepository 的 save 方法是否被“恰好一次”地调用了，
        // 并且传入的参数正是我们创建的 newBook 对象。
        // 这证明了我们的业务逻辑是正确的：当ISBN不存在时，程序会去执行保存操作。
        verify(mockBookRepository, times(1)).save(newBook);
    }

    @Test
    void addBook_shouldThrowException_whenIsbnAlreadyExists() {
        // --- 1. 准备阶段 (Arrange) ---
        Book existingBook = new Book("已存在的书", "某人", "2000", "B002");

        // 定义模拟对象的行为：
        // 当 mockBookRepository 的 findByIsbn 方法被以 "B002" 为参数调用时，
        // 那么就返回一个包含 existingBook 的 Optional (假装数据库里已经有这本书了)。
        when(mockBookRepository.findByIsbn("B002")).thenReturn(Optional.of(existingBook));

        // --- 2. & 3. 行动并断言阶段 (Act & Assert) ---
        // 我们断言 (assert) 执行 addBook(existingBook) 这个动作会“抛出” (Throws)
        // DuplicateIsbnException.class 这个类型的异常。
        // 如果 addBook 没有抛出这个异常，或者抛出了其他异常，测试就会失败。
        assertThrows(DuplicateIsbnException.class, () -> {
            bookService.addBook(existingBook);
        });

        // 验证 save 方法从未被调用，因为在它之前就应该抛出异常了。
        verify(mockBookRepository, never()).save(existingBook);
    }

    @Test
    void deleteBook_shouldCallRepository_whenIsbnExists() throws BookNotFoundException {
        // --- 1. 准备阶段 (Arrange) ---
        Book existingBook = new Book("存在的书", "某人", "2000", "B004");
        String existingIsbn = "B004";

        // 模拟行为：当仓库查找 "B004" 时，返回一个包含书的 Optional
        when(mockBookRepository.findByIsbn(existingIsbn)).thenReturn(Optional.of(existingBook));

        // --- 2. 行动 (Act) ---
        bookService.deleteBook(existingIsbn);

        // --- 3. 断言 (Assert) ---
        // 验证：因为书存在，所以仓库的 deleteByIsbn 方法必须被调用“恰好一次”，
        // 并且参数就是 "B004"
        verify(mockBookRepository, times(1)).deleteByIsbn(existingIsbn);
    }

    @Test
    void deleteBook_shouldThrowException_whenIsbnNotExists() {
        // --- 1. 准备阶段 (Arrange) ---
        String nonExistingIsbn = "B003";

        // 定义模拟行为：当仓库尝试查找 "B003" 时，返回一个空的 Optional
        when(mockBookRepository.findByIsbn(nonExistingIsbn)).thenReturn(Optional.empty());

        // --- 2. & 3. 行动并断言 (Act & Assert) ---
        // 断言：执行 deleteBook("B003") 这个动作，一定会抛出 BookNotFoundException
        assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook(nonExistingIsbn);
        });

        // 验证：因为书不存在，所以仓库的 deleteByIsbn 方法永远不应该被调用
        verify(mockBookRepository, never()).deleteByIsbn(nonExistingIsbn);
    }
}
