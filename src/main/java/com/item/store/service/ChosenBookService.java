package com.item.store.service;

import com.item.store.dto.ChosenBookDto;
import com.item.store.entity.Book;
import com.item.store.entity.ChosenBook;
import com.item.store.entity.User;
import com.item.store.repository.BookRepository;
import com.item.store.repository.ChosenCandleRepository;
import com.item.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChosenBookService {

    @Autowired
    ChosenCandleRepository chosenCandleRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    public void addToCart(ChosenBookDto chosenBookDto, String bookId, String email){
        ChosenBook chosenBook = buildProduct(chosenBookDto,bookId,email);
        chosenCandleRepository.save(chosenBook);
    }

    private ChosenBook buildProduct(ChosenBookDto chosenBookDto, String bookId,String email){

        ChosenBook chosenBook = new ChosenBook();
        chosenBook.setChosenQuantity(Integer.parseInt(chosenBookDto.getQuantity()));

        Optional<Book> book = bookRepository.findById(Integer.parseInt(bookId));
        book.ifPresent(chosenBook::setBook);

        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(value -> chosenBook.setShoppingCart(value.getShoppingCart()));
        return chosenBook;
    }
}
