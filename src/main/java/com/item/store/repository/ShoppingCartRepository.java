package com.item.store.repository;

import com.item.store.entity.ShoppingCart;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    public ShoppingCart findByUserEmail (String email);
}
