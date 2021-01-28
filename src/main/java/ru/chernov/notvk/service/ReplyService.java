package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.entity.Comment;
import ru.chernov.notvk.entity.Reply;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.repository.CommentRepository;
import ru.chernov.notvk.repository.ReplyRepository;
import ru.chernov.notvk.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@Service
public class ReplyService {

    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ReplyService(UserRepository userRepository,
                        ReplyRepository replyRepository,
                        CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
        this.commentRepository = commentRepository;
    }

    public Reply findById(long replyId) {
        return replyRepository.findById(replyId).orElse(null);
    }

    public Reply create(long userId, long commentId, String text) {
        User author = userRepository.findById(userId).orElse(null);
        Comment comment = commentRepository.findById(commentId).orElse(null);

        Reply reply = new Reply();
        reply.setCreationDateTime(LocalDateTime.now());
        reply.setAuthor(author);
        reply.setComment(comment);
        reply.setText(text);

        return replyRepository.save(reply);
    }

    public void delete(long replyId) {
        replyRepository.deleteById(replyId);
    }

    public Set<Reply> getCommentReplies(long commentId) {
        return replyRepository.findAllByCommentIdOrderByCreationDateTime(commentId);
    }

    public void like(long replyId, long userId) {
        User user = userRepository.getOne(userId);
        Reply reply = findById(replyId);

        if (!reply.getLikes().contains(user)) {
            reply.getLikes().add(user);
            replyRepository.save(reply);
        }
    }

    public void unlike(long replyId, long userId) {
        User user = userRepository.getOne(userId);
        Reply reply = findById(replyId);

        if (reply.getLikes().contains(user)) {
            reply.getLikes().remove(user);
            replyRepository.save(reply);
        }
    }
}
