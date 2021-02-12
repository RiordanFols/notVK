package ru.chernov.notvk.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.chernov.notvk.domain.Gender;
import ru.chernov.notvk.domain.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@Entity
@Table(name = "usr")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "username", "name", "surname", "avatarFilename", "email", "status", "birthdayString"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(length = 30, nullable = false)
    private String username;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;

    @Column(length = 100, nullable = false)
    private String avatarFilename;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String surname;

    @Column(length = 50)
    private String status;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonIgnore
    private LocalDate birthday;

    @Transient
    private String birthdayString;

    @Column(length = 100, nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @JsonIgnore
    private boolean isActive;

    @Column
    @JsonIgnore
    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", nullable = false))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id", updatable = false, nullable = false),
            inverseJoinColumns = @JoinColumn(name = "target_id", updatable = false, nullable = false))
    @JsonIgnore
    private Set<User> subscriptions = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "target_id", updatable = false, nullable = false),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id", updatable = false, nullable = false))
    @JsonIgnore
    private Set<User> subscribers = new HashSet<>();

    public void formatBirthday() {
        if (this.birthday != null) {
            String day = String.valueOf(this.getBirthday().getDayOfMonth());
            String month = this.getBirthday().getMonthValue() >= 10 ?
                    String.valueOf(this.getBirthday().getMonthValue()) :
                    "0" + this.getBirthday().getMonthValue();
            String year = String.valueOf(this.getBirthday().getYear());

            this.setBirthdayString(day + "." + month + "." + year);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public void setGender(String gender) {
        Gender newGender = Gender.valueOf(gender);
        // если пользователь еще не создан или имеет стоковый аватар
        if (this.getGender() == null || this.avatarFilename.equals(this.getGender().getStockAvatarFilename()))
            // ставим ему новый стоковый аватар
            this.avatarFilename = newGender.getStockAvatarFilename();

        this.gender = newGender;
    }
}
