package com.XenaOz45.ApiUniversityMS.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers", indexes = {
        @Index(name = "idx_teacher_employee_id", columnList = "employee_id"),
        @Index(name = "idx_teacher_department", columnList = "department_id"),
        @Index(name = "idx_teacher_degree", columnList = "academic_degree"),
        @Index(name = "idx_teacher_title", columnList = "academic_title"),
        @Index(name = "idx_teacher_position", columnList = "position"),
        @Index(name = "idx_teacher_department_position", columnList = "department_id,position"),
        @Index(name = "idx_teacher_department_title", columnList = "department_id,academic_title")
})
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Department department;

    @OneToMany(mappedBy = "curator", fetch = FetchType.LAZY)
    private List<Group> groups = new ArrayList<Group>();

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_degree")
    private AcademicDegree academicDegree;

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_title")
    private AcademicTitle academicTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @Column(name = "office_number")
    private String personalOfficeNumber;

    @Column(name = "work_experience")
    private int workExperience = 1;
}

