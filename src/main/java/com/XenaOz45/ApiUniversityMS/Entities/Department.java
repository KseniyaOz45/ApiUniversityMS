package com.XenaOz45.ApiUniversityMS.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments", indexes = {
        @Index(name = "idx_department_name", columnList = "name"),
        @Index(name = "idx_department_short_name", columnList = "short_name"),
        @Index(name = "idx_department_slug", columnList = "slug"),

        @Index(name = "idx_department_faculty", columnList = "faculty_id"),

        @Index(name = "idx_department_created_at", columnList = "created_at"),

        @Index(name = "idx_department_faculty_name", columnList = "faculty_id,name"),
        @Index(name = "idx_department_faculty_created", columnList = "faculty_id,created_at DESC")
})
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "short_name", nullable = false, unique = true)
    private String shortName;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "department_office")
    private String departmentOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Faculty faculty;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Group> groups = new ArrayList<Group>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Discipline> disciplines = new ArrayList<Discipline>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Teacher> teachers = new ArrayList<Teacher>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
