package com.item.store.service;

import com.item.store.dto.BookDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class BookValidator {

    public void validate(BookDto bookDto, BindingResult bindingResult) {
        if(bookDto.getAuthor().isEmpty()){
            FieldError fieldError = new FieldError("bookDto","author","Please fill mandatory fields!");
            bindingResult.addError(fieldError);
        }
        if(bookDto.getTitle().isEmpty()){
            FieldError fieldError = new FieldError("bookDto","title","Please fill mandatory fields!");
            bindingResult.addError(fieldError);
        }
        if (bookDto.getQuantity().isEmpty()){
            FieldError fieldError = new FieldError("bookDto","quantity","Please fill mandatory fields!");
            bindingResult.addError(fieldError);
        }
        if (Double.parseDouble(bookDto.getQuantity()) <= 0){
            FieldError fieldError = new FieldError("bookDto","quantity","Please fill mandatory fields!");
            bindingResult.addError(fieldError);
        }
    }
}