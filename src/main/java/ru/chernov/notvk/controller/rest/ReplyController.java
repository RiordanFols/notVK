package ru.chernov.notvk.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chernov.notvk.entity.Reply;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.service.ReplyService;

import java.util.Map;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("{id}")
    public Set<Reply> getCommentReplies(@PathVariable(name = "id") long commentId) {
        return replyService.getCommentReplies(commentId);
    }

    @PostMapping("{id}")
    public Reply create(@AuthenticationPrincipal User user,
                          @PathVariable(name = "id") long commentId,
                          @RequestBody Map<String, Object> body) {
        return replyService.create(user.getId(), commentId, (String) body.get("text"));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") long replyId) {
        replyService.delete(replyId);
    }
}
