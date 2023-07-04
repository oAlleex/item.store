package com.item.store.repository;

import com.item.store.entity.ChosenBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChosenCandleRepository extends JpaRepository<ChosenBook, Integer> {
}
