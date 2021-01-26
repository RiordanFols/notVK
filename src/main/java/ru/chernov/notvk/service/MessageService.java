package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.entity.Message;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.repository.MessageRepository;
import ru.chernov.notvk.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pavel Chernov
 */
@Service
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public Message findById(long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public Set<Message> getCorrespondence(long user1Id, long user2Id) {
        Set<Message> messages = new TreeSet<>(Comparator.comparing(Message::getCreationDateTime).reversed());
        messages.addAll(messageRepository.findAllByAuthorIdAndTargetId(user1Id, user2Id));
        messages.addAll(messageRepository.findAllByAuthorIdAndTargetId(user2Id, user1Id));

        return messages;
    }

    public Message create(long authorId, long targetId, String text) {
        User author = userRepository.findById(authorId).orElse(null);
        User target = userRepository.findById(targetId).orElse(null);

        Message message = new Message();
        message.setCreationDateTime(LocalDateTime.now());
        message.setAuthor(author);
        message.setTarget(target);
        message.setText(text);

        return messageRepository.save(message);
    }

    public void delete(long id) {
        messageRepository.deleteById(id);
    }
}
