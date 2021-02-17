package ru.chernov.notvk.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernov.notvk.service.MessageService;

import java.io.IOException;

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

    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") long messageId) throws IOException {
        messageService.delete(messageId);
    }
}
