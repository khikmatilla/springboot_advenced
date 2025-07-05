package com.myproject.springboot_advenced.resourc;

import com.myproject.springboot_advenced.dto.CommentCreateDTO;
import com.myproject.springboot_advenced.dto.CommentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "CommentClient", url = "${comments.url.base}")
public interface CommentClient {

    @GetMapping("/{id}/post")
    List<CommentDTO>getAllComments(@PathVariable("id") Integer id);

    @PostMapping("/saveAll")
    Void saveAllComments(@RequestBody List<CommentCreateDTO> dtos);



}
