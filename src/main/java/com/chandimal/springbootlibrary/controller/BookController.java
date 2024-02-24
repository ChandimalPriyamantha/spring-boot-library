package com.chandimal.springbootlibrary.controller;


import com.chandimal.springbootlibrary.entity.Book;
import com.chandimal.springbootlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping("/secure/checkout")
    public Book checkout (@RequestParam Long bookId) throws Exception{
        String userEmail = "testuser@email.com";
        return bookService.checkoutBook(userEmail,bookId);
    }
}
