package ru.chernov.notvk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.notvk.entity.Post;

import java.util.List;

/**
 * @author Pavel Chernov
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthorIdOrderByCreationDateTimeDesc(long id);
}
