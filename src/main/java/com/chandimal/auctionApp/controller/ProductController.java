package com.chandimal.auctionApp.controller;


import com.chandimal.auctionApp.entity.Product;
import com.chandimal.auctionApp.service.ProductService;
import com.chandimal.auctionApp.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    @GetMapping("secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value= "Authorization")String token){
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return productService.currentLoansCount(userEmail);
    }

    @GetMapping("secure/ischeckedout/byuser")
    public boolean checkoutBookByUser(@RequestHeader(value= "Authorization")String token, @RequestParam Long bookId){

        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return  productService.checkoutBookByUser(userEmail, bookId);

    }

    @PutMapping("/secure/checkout")
    public Product checkout (@RequestHeader(value= "Authorization")String token, @RequestParam Long bookId) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return productService.checkoutBook(userEmail,bookId);
    }
}
