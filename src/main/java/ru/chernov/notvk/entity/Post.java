package ru.chernov.notvk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(updatable = false, length = 5000, nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User author;

    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm")
    private LocalDateTime creationDateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_like",
        joinColumns = @JoinColumn(name = "post_id", nullable = false, updatable = false),
        inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false))
    @JsonIgnore
    private Set<User> likes = new HashSet<>();
}