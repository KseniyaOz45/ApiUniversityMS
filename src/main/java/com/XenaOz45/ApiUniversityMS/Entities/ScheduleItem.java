package com.XenaOz45.ApiUniversityMS.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"weekType", "dayOfWeek", "pair_number", "group_id"})
}, indexes = {
        // САМЫЕ ВАЖНЫЕ - для основных запросов по группам
        @Index(name = "idx_schedule_group_week_day", columnList = "group_id,week_type,day_of_week"),
        @Index(name = "idx_schedule_group_week", columnList = "group_id,week_type"),

        // Для запросов по преподавателям (через discipline)
        @Index(name = "idx_schedule_discipline", columnList = "discipline_id"),
        @Index(name = "idx_schedule_week_day", columnList = "week_type,day_of_week"),

        // Для запросов по аудиториям
        @Index(name = "idx_schedule_auditory_day", columnList = "auditory_number,day_of_week"),
        @Index(name = "idx_schedule_auditory", columnList = "auditory_number"),

        // Для запросов с парой и днем
        @Index(name = "idx_schedule_day_pair", columnList = "day_of_week,pair_number"),

        // Для проверки конфликтов (оптимизация сложного запроса)
        @Index(name = "idx_schedule_day_pair_group", columnList = "day_of_week,pair_number,group_id"),
        @Index(name = "idx_schedule_day_pair_auditory", columnList = "day_of_week,pair_number,auditory_number"),

        // Для пагинации
        @Index(name = "idx_schedule_created_at", columnList = "created_at")
})
public class ScheduleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WeekType weekType;

    @Enumerated(EnumType.STRING)
    private Day dayOfWeek;

    @Column(nullable = false)
    private int pairNumber;

    @Column(nullable = false)
    private String auditoryNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Discipline discipline;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

