package ru.chernov.notvk.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernov.notvk.entity.Message;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.service.MessageService;

import java.util.Set;

/**
 * @author Pavel Chernov
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("{id}")
    public Set<Message> getCorrespondence(@PathVariable(name = "id") long targetId,
                                          @AuthenticationPrincipal User user) {
        return messageService.getCorrespondence(user.getId(), targetId);
    }
}
