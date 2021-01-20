package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.entity.Post;
import ru.chernov.notvk.repository.PostRepository;
import ru.chernov.notvk.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Pavel Chernov
 */
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getUserPosts(long authorId) {
        return postRepository.findAllByAuthorIdOrderByCreationDateTimeDesc(authorId);
    }

    public List<Post> getFeed(long userId) {
        return null;
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post create(String text, long userId) {
        Post post = new Post();
        post.setAuthor(userRepository.getOne(userId));
        post.setText(text);
        post.setCreationDateTime(LocalDateTime.now());

        return postRepository.save(post);
    }

    public void delete(long id) {
        postRepository.deleteById(id);
    }

    public void likePost(long postId, long userId) {
        Post post = findById(postId);
        post.getLikes().add(userRepository.getOne(userId));
        postRepository.save(post);
    }

    public void unlikePost(long postId, long userId) {
        Post post = findById(postId);
        post.getLikes().remove(userRepository.getOne(userId));
        postRepository.save(post);
    }
}
