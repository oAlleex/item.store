package com.item.store.controller;

import com.item.store.dto.UserDto;
import com.item.store.service.UserService;
import com.item.store.service.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private UserService userService;
    private UserValidator userValidator;

    @Autowired
    public AuthController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String viewLogin() {
        return "login";
    }

    @PostMapping("register/save")
    public String register(@ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("user", userDto);
            return "register";
        }
        userService.saveUser(userDto);
        return "redirect:/login"; //daca avem erori trimitem inapoi la register
    }
    @GetMapping("/register")
    public String viewRegister (Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user",userDto);
        return "register";

    }
}
