package com.myproject.springboot_advenced;

import jakarta.validation.Valid;
import lombok.NonNull;

public interface UserService {
    User create(@NonNull User user);

    User get(@NonNull Integer id);

    void delete(Integer id);
}
