package com.myproject.springboot_advenced.book;


import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

   private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Book>> getAllWithPages(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "ASC", required = false) Sort.Direction sortDirection,
            @RequestParam String field
    ) {
        Sort sort = Sort.by(sortDirection, field);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(bookRepository.findAll(pageable));
    }

//    @GetMapping("/query")
//    public ResponseEntity<List<Book>> getAllByParams(
//            @RequestParam(defaultValue = "", required = false) String title,
//            @RequestParam(defaultValue = "0", required = false) int userId
//    ) {
//        //return ResponseEntity.ok(bookRepository.findAllByCustomTitle(title, userId));
//        String regex = "^"+ title + ".*";
//        return ResponseEntity.ok(bookRepository.findAllByTitleRegexAndUserIdGreaterThan(regex, userId));
//    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable String id) {
        Book post = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookRepository.save(book));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody Book updatingData) {
        return ResponseEntity.ok(bookRepository.update(updatingData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookRepository.deleteById(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }

}
