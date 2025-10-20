package com.XenaOz45.ApiUniversityMS.Repositories;

import com.XenaOz45.ApiUniversityMS.Entities.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    // Базовые методы с правильным ORDER BY
    List<Grade> findByStudentIdOrderByCreatedAtDesc(Long studentId);

    Page<Grade> findByStudentIdOrderByCreatedAtDesc(Long studentId, Pageable pageable);

    List<Grade> findByTeacherIdOrderByCreatedAtDesc(Long teacherId);

    Page<Grade> findByTeacherIdOrderByCreatedAtDesc(Long teacherId, Pageable pageable);

    // Комбинированные поиски с пагинацией
    Page<Grade> findByStudentIdAndDisciplineIdOrderByCreatedAtDesc(Long studentId, Long disciplineId, Pageable pageable);

    Page<Grade> findByTeacherIdAndDisciplineIdOrderByCreatedAtDesc(Long teacherId, Long disciplineId, Pageable pageable);

    // Универсальный метод для сложной фильтрации
    @Query("SELECT g FROM Grade g WHERE " +
            "(:studentId IS NULL OR g.student.id = :studentId) AND " +
            "(:teacherId IS NULL OR g.teacher.id = :teacherId) AND " +
            "(:disciplineId IS NULL OR g.discipline.id = :disciplineId) " +
            "ORDER BY g.createdAt DESC")
    Page<Grade> findByFilters(@Param("studentId") Long studentId,
                              @Param("teacherId") Long teacherId,
                              @Param("disciplineId") Long disciplineId,
                              Pageable pageable);

    // Статистические методы (для сервиса)
    @Query("SELECT AVG(g.value) FROM Grade g WHERE g.student.id = :studentId")
    Double findAverageGradeByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT AVG(g.value) FROM Grade g WHERE g.student.id = :studentId AND g.discipline.id = :disciplineId")
    Double findAverageGradeByStudentAndDiscipline(@Param("studentId") Long studentId,
                                                  @Param("disciplineId") Long disciplineId);

    @Query("SELECT g.discipline, AVG(g.value) FROM Grade g WHERE g.student.id = :studentId GROUP BY g.discipline")
    List<Object[]> findAverageGradesByDisciplineForStudent(@Param("studentId") Long studentId);

    // Вспомогательные методы для не-пагинированных результатов
    default List<Grade> findByStudentIdAndDisciplineId(Long studentId, Long disciplineId) {
        return findByStudentIdAndDisciplineIdOrderByCreatedAtDesc(studentId, disciplineId, Pageable.unpaged())
                .getContent();
    }

    default List<Grade> findByTeacherIdAndDisciplineId(Long teacherId, Long disciplineId) {
        return findByTeacherIdAndDisciplineIdOrderByCreatedAtDesc(teacherId, disciplineId, Pageable.unpaged())
                .getContent();
    }
}
