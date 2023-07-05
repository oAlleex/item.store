package com.item.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShoppingCartDto {

    private String subTotal;
    private String total;
    private List<ShoppingCartBookDto> books = new ArrayList<>();

    public void add(ShoppingCartBookDto shoppingCartBookDto){
        books.add(shoppingCartBookDto);
    }
}
