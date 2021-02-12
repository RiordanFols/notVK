package ru.chernov.notvk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.notvk.domain.entity.Post;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pavel Chernov
 */
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getUserPosts(long authorId) {
        return postRepository.findAllByAuthorIdOrderByCreationDateTimeDesc(authorId);
    }

    public Set<Post> getFeed(long userId) {
        User user = userService.findById(userId);
        Set<Post> feed = new TreeSet<>(Comparator.comparing(Post::getCreationDateTime).reversed());
        for (var subscription : user.getSubscriptions()) {
            feed.addAll(getUserPosts(subscription.getId()));
        }
        return feed;
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post create(String text, long userId) {
        Post post = new Post();
        post.setAuthor(userService.findById(userId));
        post.setText(text);
        post.setCreationDateTime(LocalDateTime.now());

        return postRepository.save(post);
    }

    public void delete(long id) {
        postRepository.deleteById(id);
    }

    public void likePost(long postId, long userId) {
        Post post = findById(postId);
        User user = userService.findById(userId);

        if (!post.getLikes().contains(user)) {
            post.getLikes().add(user);
            postRepository.save(post);
        }
    }

    public void unlikePost(long postId, long userId) {
        Post post = findById(postId);
        User user = userService.findById(userId);

        if (post.getLikes().contains(user)) {
            post.getLikes().remove(user);
            postRepository.save(post);
        }
    }
}
