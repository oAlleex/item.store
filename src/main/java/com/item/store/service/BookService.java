package com.item.store.service;

import com.item.store.entity.Book;
import com.item.store.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> listAllBooks(){
        return bookRepository.findAll();
    }
}
