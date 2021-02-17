package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.chernov.notvk.domain.entity.Message;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.repository.MessageRepository;
import ru.chernov.notvk.utils.ImageUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pavel Chernov
 */
@Service
public class MessageService {

    private final UserService userService;
    private final MessageRepository messageRepository;
    private final FileService fileService;

    @Autowired
    public MessageService(UserService userService, MessageRepository messageRepository, FileService fileService) {
        this.userService = userService;
        this.messageRepository = messageRepository;
        this.fileService = fileService;
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

    public Message create(long authorId, long targetId, String text, MultipartFile[] images) throws IOException {
        User author = userService.findById(authorId);
        User target = userService.findById(targetId);

        Message message = new Message();
        message.setCreationDateTime(LocalDateTime.now());
        message.setAuthor(author);
        message.setTarget(target);
        message.setText(text);

        for(var image: images) {
            // если тип файла соответсвует изображению и кол-во файлов в сообщении меньше допустимого максимума
            if (ImageUtils.isImageTypeAllowed(image) && message.getImgFilenames().size() < Message.MAX_IMAGES) {
                // сохраняем изображение
                String filename = fileService.saveImage(image);
                message.getImgFilenames().add(filename);
            }
        }

        return messageRepository.save(message);
    }

    public void delete(long messageId) throws IOException {
        Message message = findById(messageId);
        for (var filename: message.getImgFilenames()) {
            fileService.deleteImage(filename);
        }

        messageRepository.deleteById(messageId);
    }
}
