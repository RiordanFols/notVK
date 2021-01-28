package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.entity.Comment;
import ru.chernov.notvk.entity.Post;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.repository.CommentRepository;
import ru.chernov.notvk.repository.PostRepository;
import ru.chernov.notvk.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(UserRepository userRepository,
                          CommentRepository commentRepository,
                          PostRepository postRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment findById(long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment create(long userId, long postId, String text) {
        User author = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        Comment comment = new Comment();
        comment.setCreationDateTime(LocalDateTime.now());
        comment.setText(text);
        comment.setAuthor(author);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public void delete(long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Set<Comment> getPostComments(long postId) {
        return commentRepository.findAllByPostIdOrderByCreationDateTime(postId);
    }

    public void likeComment(long commentId, long userId) {
        User user = userRepository.getOne(userId);
        Comment comment = findById(commentId);

        if (!comment.getLikes().contains(user)) {
            comment.getLikes().add(user);
            commentRepository.save(comment);
        }
    }

    public void unlikeComment(long commentId, long userId) {
        User user = userRepository.getOne(userId);
        Comment comment = findById(commentId);

        if (comment.getLikes().contains(user)) {
            comment.getLikes().remove(user);
            commentRepository.save(comment);
        }
    }
}
