package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
  Optional<QuestionAnswer> findByQuestionIgnoreCase(String question);
}