package com.myproject.springboot_advenced.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
public class Authority implements Serializable {
    @Id
    private String name;

}
