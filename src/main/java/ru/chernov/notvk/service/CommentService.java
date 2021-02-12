package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.domain.entity.Comment;
import ru.chernov.notvk.domain.entity.Post;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.repository.CommentRepository;
import ru.chernov.notvk.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pavel Chernov
 */
@Service
public class CommentService {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(UserService userService,
                          CommentRepository commentRepository,
                          PostRepository postRepository) {
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment findById(long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment create(long userId, long postId, String text) {
        User author = userService.findById(userId);
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
        Set<Comment> comments = new TreeSet<>(Comparator
                .comparingInt((Comment c) -> c.getLikes().size())
                .reversed()
                .thenComparing(Comment::getCreationDateTime));
        comments.addAll(commentRepository.findAllByPostId(postId));
        return comments;
    }

    public void like(long commentId, long userId) {
        User user = userService.findById(userId);
        Comment comment = findById(commentId);

        if (!comment.getLikes().contains(user)) {
            comment.getLikes().add(user);
            commentRepository.save(comment);
        }
    }

    public void unlike(long commentId, long userId) {
        User user = userService.findById(userId);
        Comment comment = findById(commentId);

        if (comment.getLikes().contains(user)) {
            comment.getLikes().remove(user);
            commentRepository.save(comment);
        }
    }
}
