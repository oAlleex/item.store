package com.item.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerOderId;
    private String shippingAddress;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "customerOrder")
    private List<ChosenBook> chosenBook;
}
