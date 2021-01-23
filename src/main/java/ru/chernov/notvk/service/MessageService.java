package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.entity.Message;
import ru.chernov.notvk.repository.MessageRepository;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pavel Chernov
 */
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message findById(long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public Set<Message> getCorrespondence(long user1Id, long user2Id) {
        Set<Message> messages =
                messageRepository.findAllByAuthorIdAndTargetIdOrderByCreationDateTime(user1Id, user2Id);
        messages.addAll(messageRepository.findAllByAuthorIdAndTargetIdOrderByCreationDateTime(user2Id, user1Id));

        return messages;
    }
}
