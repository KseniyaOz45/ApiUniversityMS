package com.XenaOz45.ApiUniversityMS.Repositories;

import com.XenaOz45.ApiUniversityMS.Entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByEmployeeId(String employeeId);
    Optional<Teacher> findByEmployeeId(String employeeId);

    List<Teacher> findAllByDepartmentIdOrderByLastName(Long departmentId);
    Page<Teacher> findAllByDepartmentIdOrderByLastName(Long departmentId, Pageable pageable);

    List<Teacher> findAllByAcademicDegreeOrderByLastName(AcademicDegree academicDegree);
    Page<Teacher> findAllByAcademicDegreeOrderByLastName(AcademicDegree academicDegree, Pageable pageable);

    List<Teacher> findAllByAcademicTitleOrderByLastName(AcademicTitle academicTitle);
    Page<Teacher> findAllByAcademicTitleOrderByLastName(AcademicTitle academicTitle, Pageable pageable);

    List<Teacher> findAllByPositionOrderByLastName(Position position);
    Page<Teacher> findAllByPositionOrderByLastName(Position position, Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE " +
            "LOWER(CONCAT(t.firstName, ' ', t.lastName)) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(t.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(t.lastName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Teacher> findByFullNameContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    // Комплексный поиск для админки
    @Query("SELECT t FROM Teacher t WHERE " +
            "(:departmentId IS NULL OR t.department.id = :departmentId) AND " +
            "(:position IS NULL OR t.position = :position) AND " +
            "(:academicTitle IS NULL OR t.academicTitle = :academicTitle) AND " +
            "(:academicDegree IS NULL OR t.academicDegree = :academicDegree) AND " +
            "(:search IS NULL OR LOWER(CONCAT(t.firstName, ' ', t.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Teacher> findByFilters(@Param("departmentId") Long departmentId,
                                @Param("position") Position position,
                                @Param("academicTitle") AcademicTitle academicTitle,
                                @Param("academicDegree") AcademicDegree academicDegree,
                                @Param("search") String search,
                                Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE EXISTS " +
            "(SELECT g FROM Group g WHERE g.curator = t)")
    List<Teacher> findCurators();

    @Query("SELECT t FROM Teacher t WHERE NOT EXISTS " +
            "(SELECT g FROM Group g WHERE g.curator = t)")
    List<Teacher> findNotCurators();

    // Можно добавить для UI таблиц:
    @Query("SELECT t FROM Teacher t WHERE EXISTS " +
            "(SELECT g FROM Group g WHERE g.curator = t)")
    Page<Teacher> findCurators(Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE NOT EXISTS " +
            "(SELECT g FROM Group g WHERE g.curator = t)")
    Page<Teacher> findNotCurators(Pageable pageable);

    @Query("SELECT t.department.name, COUNT(t) FROM Teacher t GROUP BY t.department.name")
    List<Object[]> countTeachersByDepartment();

    @Query("SELECT t.position, COUNT(t) FROM Teacher t GROUP BY t.position")
    List<Object[]> countTeachersByPosition();

    @Query("SELECT t.academicDegree, COUNT(t) FROM Teacher t GROUP BY t.academicDegree")
    List<Object[]> countTeachersByAcademicDegree();
}
