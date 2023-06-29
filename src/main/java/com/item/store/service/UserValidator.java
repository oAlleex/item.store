package com.item.store.service;

import com.item.store.dto.UserDto;
import com.item.store.entity.User;
import com.item.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Service
public class UserValidator {

    @Autowired
    UserRepository userRepository;
    public void validate(UserDto userDto, BindingResult bindingResult){
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())){
            FieldError fieldError = new FieldError("userDto", "confirmPassword", "Passwords don't match");
            bindingResult.addError(fieldError);
        }

        Optional <User> userOptional =  userRepository.findByEmail(userDto.getEmail());

        if(userOptional.isPresent()){
            FieldError fieldError = new FieldError("userDto", "email", "Email already exists");
            bindingResult.addError(fieldError);
        }
    }
}