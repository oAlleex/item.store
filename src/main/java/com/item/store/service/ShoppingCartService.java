package com.item.store.service;

import com.item.store.dto.ShoppingCartBookDto;
import com.item.store.dto.ShoppingCartDto;
import com.item.store.entity.ChosenBook;
import com.item.store.entity.ShoppingCart;
import com.item.store.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartDto getShoppingCartByUserEmail(String email){
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email);
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();

        Double subTotal = 0.0;

        for(ChosenBook chosenBook : shoppingCart.getChosenBooks()){
            ShoppingCartBookDto shoppingCartBookDto = ShoppingCartBookDto.builder()
                    .name(chosenBook.getBook().getTitle())
                    .price(String.valueOf(chosenBook.getBook().getPrice()))
                    .quantity(String.valueOf(chosenBook.getChosenQuantity())).build();

            Double auxPrice = chosenBook.getChosenQuantity() * chosenBook.getBook().getPrice();
            shoppingCartDto.setTotal(String.valueOf(auxPrice));

            subTotal += auxPrice;

            shoppingCartBookDto.setTotal(String.valueOf(auxPrice));
            shoppingCartDto.add(shoppingCartBookDto);
        }

        shoppingCartDto.setSubTotal(String.valueOf(subTotal));
        shoppingCartDto.setTotal(String.valueOf(subTotal + 50));

        return shoppingCartDto;
    }
}