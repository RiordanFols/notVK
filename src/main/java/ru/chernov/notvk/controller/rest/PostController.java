package ru.chernov.notvk.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.chernov.notvk.domain.entity.Post;
import ru.chernov.notvk.service.PostService;

import java.io.IOException;

/**
 * @author Pavel Chernov
 */
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("{id}")
    public Post getOne(@PathVariable long id) {
        return postService.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) throws IOException {
        postService.delete(id);
    }
}
