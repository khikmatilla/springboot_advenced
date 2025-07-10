package com.myproject.springboot_advenced.user;

import com.myproject.springboot_advenced.posts.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersRepository usersRepository;
    private final PostRepository postRepository;

    @GetMapping
    public List<Users> getUsers() {
        List<Users> authors = usersRepository.findAll();
        return authors;
    }

    @PutMapping
    public Users updateUser(@RequestBody UsersUpdateDto dto) {
        Users user = usersRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("User id not found"));
        if (dto.getFirstName() != null) {
          user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        return usersRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        usersRepository.deleteById(id);
    }

}
