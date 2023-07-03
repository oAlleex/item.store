package com.item.store.controller;

import com.item.store.dto.BookDto;
import com.item.store.entity.Book;
import com.item.store.service.BookService;
import com.item.store.service.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;
    @Autowired
    private BookValidator bookValidator;

    @GetMapping("/")
    public String viewTemplate(Model model) {
        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (authenticated) {
            List<Book> books = bookService.listAllBooks();
            model.addAttribute("books", books);
//            ChosenItemDto chosenItemDto = new ChosenItemDto();
//            model.addAttribute("chosenItemDto", chosenItemDto);
            return "books";
        } else {
            return "login";
        }
    }

    @GetMapping("/books")
    public String viewAllBooks(Model model) {
        List<Book> bookList = bookService.listAllBooks();
        model.addAttribute("books", bookList);
        return "books";
    }

    @GetMapping("/book")
    public String viewCandleForm(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("book", bookDto);
        return "book";
    }

    @PostMapping("/book/save")
    public String saveCandle(
            @ModelAttribute("book") BookDto bookDto,
            BindingResult bindingResult,
            Model model,
            @RequestParam("coverImage") MultipartFile file
    ) throws IOException {
        bookValidator.validate(bookDto,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("book",bookDto);
            return "book";
        }
        bookService.saveBook(bookDto,file);
        List<Book> list = bookService.listAllBooks();
        model.addAttribute("books",list);
        return "redirect:/books";
    }
}