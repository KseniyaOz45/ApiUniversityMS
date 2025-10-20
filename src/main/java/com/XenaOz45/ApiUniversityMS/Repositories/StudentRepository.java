package com.XenaOz45.ApiUniversityMS.Repositories;

import com.XenaOz45.ApiUniversityMS.Entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByGradeBookNum(String gradeBookNum);
    boolean existsByGradeBookNum(String gradeBookNum);

    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(CONCAT(s.firstName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Student> findByFullNameContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    List<Student> findByGroupIdOrderByLastNameAsc(Long groupId);
    Page<Student> findByGroupIdOrderByLastNameAsc(Long groupId, Pageable pageable);

    List<Student> findAllByCourseOrderByLastNameAsc(int course);
    Page<Student> findAllByCourseOrderByLastNameAsc(int course, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.group.department.id = :departmentId")
    List<Student> findByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT s FROM Student s WHERE s.group.department.id = :departmentId")
    Page<Student> findByDepartmentId(@Param("departmentId") Long departmentId, Pageable pageable);

    Page<Student> findByAverageScoreGreaterThanEqual(double minScore, Pageable pageable);

    List<Student> findByScholarshipIsNotNull();

    @Query("SELECT s FROM Student s WHERE " +
            "(:groupId IS NULL OR s.group.id = :groupId) AND " +
            "(:course IS NULL OR s.course = :course) AND " +
            "(:semester IS NULL OR s.semester = :semester)")
    Page<Student> findByFilters(@Param("groupId") Long groupId,
                                @Param("course") int course,
                                @Param("semester") int semester,
                                Pageable pageable);

    // Можно добавить для отчетности:
    @Query("SELECT s.course, COUNT(s), AVG(s.averageScore) FROM Student s GROUP BY s.course")
    List<Object[]> getStudentStatsByCourse();

    @Query("SELECT s.group.department, COUNT(s) FROM Student s GROUP BY s.group.department")
    List<Object[]> getStudentCountByDepartment();

    @Query("SELECT COUNT(s) FROM Student s WHERE s.group.id = :groupId")
    Long countByGroupId(@Param("groupId") Long groupId);
}
