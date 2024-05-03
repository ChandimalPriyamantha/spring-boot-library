package com.chandimal.auctionApp.service;


import com.chandimal.auctionApp.dao.ProductRepository;
import com.chandimal.auctionApp.dao.CheckoutRepository;
import com.chandimal.auctionApp.entity.Product;
import com.chandimal.auctionApp.entity.Checkout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;
    private CheckoutRepository checkoutRepository;


    public ProductService(ProductRepository productRepository, CheckoutRepository checkoutRepository) {
        this.productRepository = productRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public Product checkoutBook (String userEmail, Long bookId) throws Exception{

        Optional<Product> book = productRepository.findById(bookId);
        Checkout validateCheckout  = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0){

            throw  new Exception("Book doesn't exist or already checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        productRepository.save(book.get());

        Checkout checkout = new Checkout(

                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()

        );

        checkoutRepository.save(checkout);

        return book.get();
        
    }

    public Boolean checkoutBookByUser(String userEmail, Long bookId){
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail,bookId);
        if(validateCheckout != null){
            return  true;
        }else{
            return false;
        }
    }

    public int currentLoansCount(String userEmail){
        return checkoutRepository.findBookByUserEmail(userEmail).size();
    }
}
