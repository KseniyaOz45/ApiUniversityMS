package com.XenaOz45.ApiUniversityMS.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faculties", indexes = {
        // Уникальные поля
        @Index(name = "idx_faculty_name", columnList = "name"),
        @Index(name = "idx_faculty_short_name", columnList = "short_name"),
        @Index(name = "idx_faculty_slug", columnList = "slug"),

        @Index(name = "idx_faculty_created_at", columnList = "created_at"),

        @Index(name = "idx_faculty_name_asc", columnList = "name")
})
@Getter
@Setter
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "short_name", nullable = false, unique = true)
    private String shortName;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "dean_office")
    private String deanOffice;

    @OneToMany(mappedBy = "faculty",fetch = FetchType.LAZY)
    private List<Department> departments = new ArrayList<Department>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
