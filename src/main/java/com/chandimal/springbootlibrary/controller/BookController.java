package com.chandimal.springbootlibrary.controller;


import com.chandimal.springbootlibrary.entity.Book;
import com.chandimal.springbootlibrary.service.BookService;
import com.chandimal.springbootlibrary.utils.ExtractJWT;
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

    @GetMapping("secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value= "Authorization")String token){
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("secure/ischeckedout/byuser")
    public boolean checkoutBookByUser(@RequestHeader(value= "Authorization")String token, @RequestParam Long bookId){

        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        System.out.println(userEmail);
        return  bookService.checkoutBookByUser(userEmail, bookId);

    }

    @PutMapping("/secure/checkout")
    public Book checkout (@RequestHeader(value= "Authorization")String token, @RequestParam Long bookId) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return bookService.checkoutBook(userEmail,bookId);
    }
}