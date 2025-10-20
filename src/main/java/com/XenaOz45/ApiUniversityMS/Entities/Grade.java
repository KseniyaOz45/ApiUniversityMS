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
@Table(name = "grades", indexes = {
        @Index(name = "idx_grade_student", columnList = "student_id"),
        @Index(name = "idx_grade_teacher", columnList = "teacher_id"),
        @Index(name = "idx_grade_discipline", columnList = "discipline_id"),
        @Index(name = "idx_grade_created_at", columnList = "created_at"),
        @Index(name = "idx_grade_student_discipline", columnList = "student_id,discipline_id"),
        @Index(name = "idx_grade_teacher_discipline", columnList = "teacher_id,discipline_id"),
        @Index(name = "idx_grade_student_created", columnList = "student_id,created_at DESC"),
        @Index(name = "idx_grade_teacher_created", columnList = "teacher_id,created_at DESC"),
        @Index(name = "idx_grade_student_discipline_created", columnList = "student_id,discipline_id,created_at DESC")
})
@Getter
@Setter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int value = 90;

    @Enumerated(EnumType.STRING)
    private GradeLetter letterValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private GradeType gradeType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}

