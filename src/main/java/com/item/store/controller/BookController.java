package com.item.store.controller;

import com.item.store.entity.Book;
import com.item.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String viewTemplate(Model model) {
        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(authenticated){
            List<Book> books = bookService.listAllBooks();
            model.addAttribute("books", books);
//            ChosenItemDto chosenItemDto = new ChosenItemDto();
//            model.addAttribute("chosenItemDto", chosenItemDto);
            return "books";
        }else{
            return "login";
        }
    }

    @GetMapping("/books")
    public String viewAllBooks(Model model){
        List<Book> bookList = bookService.listAllBooks();
        model.addAttribute("books",bookList);
        return "books";
    }
}