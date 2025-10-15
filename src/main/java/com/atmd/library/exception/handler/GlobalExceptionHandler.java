package com.atmd.library.exception.handler;

import com.atmd.library.exception.BookNotFoundException;
import com.atmd.library.exception.DuplicateIsbnException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 这个方法专门用来处理 BookNotFoundException 类型的异常。
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request){

        //1.创建一个结构化的Map来存放错误信息
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value()); // 404
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());// 获取异常的原始信息
        body.put("path", request.getDescription(false).replace("uri=",""));// 获取请求的URL

        //将Map作为响应体,附带404状态码
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    /**
     * 这个方法专门用来处理 DuplicateIsbnException 类型的异常。
     * HTTP 409 Conflict 状态码表示请求冲突（例如，尝试创建一个已存在的资源）。
     */
    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<Object> handleDuplicateIsbnException(DuplicateIsbnException ex, WebRequest request){

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value()); // 409
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}
