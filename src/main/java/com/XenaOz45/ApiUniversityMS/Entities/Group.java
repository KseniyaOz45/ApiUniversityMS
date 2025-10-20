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
@Table(name = "groups", indexes = {
        @Index(name = "idx_group_number", columnList = "number"),
        @Index(name = "idx_group_slug", columnList = "slug"),

        @Index(name = "idx_group_department", columnList = "department_id"),

        @Index(name = "idx_group_curator", columnList = "curator_id"),

        @Index(name = "idx_group_created_at", columnList = "created_at"),

        @Index(name = "idx_group_department_created", columnList = "department_id,created_at DESC"),
        @Index(name = "idx_group_department_number", columnList = "department_id,number"),

        @Index(name = "idx_group_number_search", columnList = "number")
})
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false, unique = true)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curator_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Teacher curator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<Student>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
