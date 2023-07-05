package com.item.store.service;

import com.item.store.entity.ChosenBook;
import com.item.store.entity.CustomerOrder;
import com.item.store.entity.ShoppingCart;
import com.item.store.entity.User;
import com.item.store.repository.ChosenBookRepository;
import com.item.store.repository.CustomerOrderRepository;
import com.item.store.repository.ShoppingCartRepository;
import com.item.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerOrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ChosenBookRepository chosenBookRepository;

    public void addCustomerOrder(String email, String shippingAddress){
        Optional<User> user = userRepository.findByEmail(email);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setUser(user.get());
        customerOrder.setShippingAddress(shippingAddress);

        customerOrderRepository.save(customerOrder);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email);
        for(ChosenBook chosenBook : shoppingCart.getChosenBooks()){
            chosenBook.setShoppingCart(null);
            chosenBook.setCustomerOrder(customerOrder);

            chosenBookRepository.save(chosenBook);
        }
    }
}
