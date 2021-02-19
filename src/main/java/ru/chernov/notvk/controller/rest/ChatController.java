package ru.chernov.notvk.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.service.MessageService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Chernov
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final MessageService messageService;

    @Autowired
    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("{id}")
    public Map<String, String> getLastMessageInfo(@AuthenticationPrincipal User user,
                                                  @PathVariable(name = "id") long targetId) {

        return messageService.getLastMessageInfo(user.getId(), targetId);
    }
}
