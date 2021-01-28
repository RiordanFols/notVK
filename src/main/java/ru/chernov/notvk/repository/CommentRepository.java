package ru.chernov.notvk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.notvk.entity.Comment;

import java.util.Set;

/**
 * @author Pavel Chernov
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Set<Comment> findAllByPostIdOrderByCreationDateTime(long id);
}
