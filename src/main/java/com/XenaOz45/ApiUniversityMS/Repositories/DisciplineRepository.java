package com.XenaOz45.ApiUniversityMS.Repositories;

import com.XenaOz45.ApiUniversityMS.Entities.Discipline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    boolean existsByName(String disciplineName);
    boolean existsByCode(String disciplineCode);
    boolean existsBySlug(String slug);

    Optional<Discipline> findByName(String disciplineName);
    Optional<Discipline> findByCode(String disciplineCode);
    Optional<Discipline> findBySlug(String slug);

    List<Discipline> findAllByDepartmentIdAndCourseOrderByNameAsc(Long departmentId,
                                                                       int course);
    Page<Discipline> findAllByDepartmentIdAndCourseOrderByNameAsc(Long departmentId,
                                                                       int course, Pageable pageable);

    List<Discipline> findAllByDepartmentIdAndCourseAndSemesterOrderByNameAsc(Long departmentId, int course,
                                                                                  int semester);
    Page<Discipline> findAllByDepartmentIdAndCourseAndSemesterOrderByNameAsc(Long departmentId, int course,
                                                                                  int semester, Pageable pageable);

    @Query("SELECT DISTINCT d FROM Discipline d WHERE d.lector.id = :teacherId OR d.assistant.id = :teacherId")
    List<Discipline> findAllByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT DISTINCT d FROM Discipline d WHERE d.lector.id = :teacherId OR d.assistant.id = :teacherId")
    Page<Discipline> findAllByTeacherId(@Param("teacherId") Long teacherId, Pageable pageable);

    Page<Discipline> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT d FROM Discipline d WHERE " +
            "(:departmentId IS NULL OR d.department.id = :departmentId) AND " +
            "(:course IS NULL OR d.course = :course) AND " +
            "(:semester IS NULL OR d.semester = :semester) AND " +
            "(:search IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Discipline> findByFilters(@Param("departmentId") Long departmentId,
                                   @Param("course") Integer course,
                                   @Param("semester") Integer semester,
                                   @Param("search") String search,
                                   Pageable pageable);
}
