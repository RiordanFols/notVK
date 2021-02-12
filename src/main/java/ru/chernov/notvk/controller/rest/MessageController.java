package ru.chernov.notvk.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chernov.notvk.domain.entity.Message;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.service.MessageService;

import java.util.Map;

/**
 * @author Pavel Chernov
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("{id}")
    public Message create(@AuthenticationPrincipal User user,
                          @PathVariable(name = "id") long targetId,
                          @RequestBody Map<String, Object> body) {
        return messageService.create(user.getId(), targetId, (String) body.get("text"));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") long messageId) {
        messageService.delete(messageId);
    }
}
