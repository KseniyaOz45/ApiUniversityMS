package com.XenaOz45.ApiUniversityMS.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "disciplines", indexes = {
        // Уникальные поля
        @Index(name = "idx_discipline_name", columnList = "name"),
        @Index(name = "idx_discipline_slug", columnList = "slug"),
        @Index(name = "idx_discipline_code", columnList = "code"),

        // Самые частые запросы - по department_id + course/semester
        @Index(name = "idx_discipline_department_course", columnList = "department_id,course"),
        @Index(name = "idx_discipline_department_semester", columnList = "department_id,semester"),
        @Index(name = "idx_discipline_department_course_semester", columnList = "department_id,course,semester"),

        // Для поиска по преподавателям
        @Index(name = "idx_discipline_lector", columnList = "lector_id"),
        @Index(name = "idx_discipline_assistant", columnList = "assistant_id"),

        // Для комбинированных запросов по преподавателям
        @Index(name = "idx_discipline_lector_assistant", columnList = "lector_id,assistant_id"),

        // Для пагинации и сортировки
        @Index(name = "idx_discipline_created_at", columnList = "created_at"),

        // Для фильтрации по курсу/семестру без department
        @Index(name = "idx_discipline_course_semester", columnList = "course,semester")
        })
@Getter
@Setter
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "hours", nullable = false)
    private double teachingHours;

    @Column(nullable = false)
    private int course;

    @Column(nullable = false)
    private int semester;

    @Enumerated(EnumType.STRING)
    private DisciplineType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lector_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Teacher lector;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assistant_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Teacher assistant;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

