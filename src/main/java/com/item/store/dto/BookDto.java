package com.item.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    private String title;
    private String author;
    private String description;
    private String price;
    private String quantity;
    private String image;
}
