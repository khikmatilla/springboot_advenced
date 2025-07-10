package com.myproject.springboot_advenced.posts;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Rating {

    ONE_STAR("⭐", "1"),
    TWO_STARS("⭐⭐", "2"),
    THREE_STARS("⭐⭐⭐", "3"),
    FOUR_STARS("⭐⭐⭐⭐", "4"),
    FIVE_STARS("⭐⭐⭐⭐⭐", "5");

    private String star;
    private String rate;
}
