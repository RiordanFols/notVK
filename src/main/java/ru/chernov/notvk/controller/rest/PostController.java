package ru.chernov.notvk.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chernov.notvk.entity.Post;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.service.PostService;

import java.util.Map;

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

    @PostMapping
    public Post create(@RequestBody Map<String, Object> body,
                       @AuthenticationPrincipal User user) {
        return postService.create((String) body.get("text"), user.getId());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        postService.delete(id);
    }
}
