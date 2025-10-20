package com.XenaOz45.ApiUniversityMS.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "students", indexes = {
        @Index(name = "idx_student_grade_book", columnList = "grade_book_num"),
        @Index(name = "idx_student_group", columnList = "group_id"),
        @Index(name = "idx_student_course", columnList = "course"),
        @Index(name = "idx_student_average_score", columnList = "average_score"),
        @Index(name = "idx_student_group_course", columnList = "group_id,course"),
        @Index(name = "idx_student_course_semester", columnList = "course,semester")
        // УДАЛЕНО: все индексы с last_name и first_name
})
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(name = "grade_book_num", nullable = false, unique = true)
    private String gradeBookNum;

    @Column(nullable = false)
    private int course;

    @Column(nullable = false)
    private int semester;

    @Column(name = "average_score", nullable = false)
    private double averageScore = 0.0;

    private double scholarship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Group group;

}
