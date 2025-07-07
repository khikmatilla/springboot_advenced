package com.myproject.springboot_advenced.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String TASKS_SELECT_QUERY = "select * from tasks";

    public List<Tasks> getAll() {
        return jdbcTemplate.query(TASKS_SELECT_QUERY, BeanPropertyRowMapper.newInstance(Tasks.class));
    }
}
