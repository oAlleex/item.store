package com.item.store.controller;

import com.item.store.dto.*;
import com.item.store.entity.Book;
import com.item.store.entity.ChosenBook;
import com.item.store.entity.User;
import com.item.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookValidator bookValidator;
    @Autowired
    private ChosenBookService chosenBookService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerOrderService customerOrderService;

    @GetMapping("/")
    public String viewTemplate(Model model) {
        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (authenticated) {
            List<Book> books = bookService.listAllBooks();
            model.addAttribute("books", books);
            ChosenBookDto chosenBookDto = ChosenBookDto.builder().build();
            model.addAttribute("chosenBookDto", chosenBookDto);
            return "books";
        } else {
            return "login";
        }
    }

    @GetMapping("/books")
    public String viewAllBooks(Model model) {
        List<Book> bookList = bookService.listAllBooks();
        model.addAttribute("books", bookList);
        ChosenBookDto chosenBookDto = ChosenBookDto.builder().build();
        model.addAttribute("chosenBookDto", chosenBookDto);
        return "books";
    }

    @GetMapping("/book")
    public String viewBookForm(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("book", bookDto);
        return "book";
    }

    @PostMapping("/book/save")
    public String saveBook(
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
        ChosenBookDto chosenBookDto = ChosenBookDto.builder().build();
        model.addAttribute("chosenBookDto", chosenBookDto);
        return "redirect:/books";
    }

    @PostMapping("/book/{bookId}")
    public String addToShoppingList(
            @PathVariable(value = "bookId") String bookId,
            @ModelAttribute ChosenBookDto chosenBookDto,
            BindingResult bindingResult,
            Model model
            ){
        model.addAttribute("chosenBookDto",chosenBookDto);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        chosenBookService.addToCart(chosenBookDto,bookId,email);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String getCart(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartByUserEmail(email);
        model.addAttribute("shoppingCartDto",shoppingCartDto);
        return "cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartByUserEmail(email);
        model.addAttribute("shoppingCartDto",shoppingCartDto);

        UserDetailsDto userDetailsDto = userService.getUserDto(email);
        model.addAttribute("userDetailsDto",userDetailsDto);

        return "checkout";
    }

    @PostMapping("/sendOrder")
    public String sendOrder(@ModelAttribute("userDetailsDto") UserDetailsDto userDetailsDto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        customerOrderService.addCustomerOrder(email,userDetailsDto.getShippingAddress());

        return "confirmation";
    }
}