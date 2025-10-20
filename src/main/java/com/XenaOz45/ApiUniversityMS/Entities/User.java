package com.XenaOz45.ApiUniversityMS.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", indexes = {
        // Уникальные поля (уже есть UNIQUE constraints)
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_slug", columnList = "slug"),
        @Index(name = "idx_user_phone", columnList = "phone_number"),
        @Index(name = "idx_user_photo", columnList = "photo_url"),

        // САМЫЕ ВАЖНЫЕ - для поиска и сортировки
        @Index(name = "idx_user_last_name", columnList = "last_name"),
        @Index(name = "idx_user_first_name", columnList = "first_name"),

        // Композитные индексы для частых комбинаций
        @Index(name = "idx_user_first_last_name", columnList = "first_name,last_name"),
        @Index(name = "idx_user_last_first_name", columnList = "last_name,first_name"),

        // Для фильтрации по ролям
        @Index(name = "idx_user_role", columnList = "role"),

        // Для сортировки по алфавиту
        @Index(name = "idx_user_last_name_asc", columnList = "last_name"),

        // Для пагинации
        @Index(name = "idx_user_created_at", columnList = "created_at")
})
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String photoUrl;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }
}

