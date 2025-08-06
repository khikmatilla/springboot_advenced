package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}