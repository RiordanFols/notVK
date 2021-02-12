package ru.chernov.notvk.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chernov.notvk.domain.entity.Comment;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.service.CommentService;

import java.util.Map;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{id}")
    public Set<Comment> getPostComments(@PathVariable(name = "id") long postId) {
        return commentService.getPostComments(postId);
    }

    @PostMapping("{id}")
    public Comment create(@AuthenticationPrincipal User user,
                          @PathVariable(name = "id") long postId,
                          @RequestBody Map<String, Object> body) {
        return commentService.create(user.getId(), postId, (String) body.get("text"));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") long commentId) {
        commentService.delete(commentId);
    }
}
