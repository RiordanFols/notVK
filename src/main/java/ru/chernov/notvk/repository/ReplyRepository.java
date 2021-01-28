package ru.chernov.notvk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.notvk.entity.Reply;

import java.util.Set;

/**
 * @author Pavel Chernov
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Set<Reply> findAllByCommentIdOrderByCreationDateTime(long id);
}
