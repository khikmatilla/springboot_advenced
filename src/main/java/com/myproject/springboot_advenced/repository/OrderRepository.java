package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

//    @Query("select o from Order o where o.contact = ?1")
//    Optional<Order> findByPhoneNumber(String phoneNum);
//
//    Optional<Order> findByContact(String contact);
}