package com.myproject.springboot_advenced.repository;


import com.myproject.springboot_advenced.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

}