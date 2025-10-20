package com.XenaOz45.ApiUniversityMS.Repositories;

import com.XenaOz45.ApiUniversityMS.Entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String departmentName);
    boolean existsByShortName(String shortName);
    boolean existsBySlug(String slug);

    Optional<Department> findByName(String departmentName);
    Optional<Department> findByShortName(String shortName);
    Optional<Department> findBySlug(String slug);

    List<Department> findAllByFacultyId(Long facultyId);
    Page<Department> findAllByFacultyId(Long facultyId, Pageable pageable);

    Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT d, COUNT(t) FROM Department d LEFT JOIN d.teachers t GROUP BY d")
    Page<Object[]> findDepartmentsWithTeacherCount(Pageable pageable);

    @Query("SELECT d, COUNT(g) FROM Department d LEFT JOIN d.groups g GROUP BY d")
    Page<Object[]> findDepartmentsWithGroupCount(Pageable pageable);

    @Query("SELECT d, COUNT(di) FROM Department d LEFT JOIN d.disciplines di GROUP BY d")
    Page<Object[]> findDepartmentsWithDisciplineCount(Pageable pageable);

    @Query("""
        SELECT d AS department, COUNT(s) AS studentCount
        FROM Department d
        JOIN d.groups g
        JOIN g.students s
        GROUP BY d
    """)
    Page<Department> findDepartmentsWithStudentCount(Pageable pageable);

    @Query("SELECT d FROM Department d WHERE " +
            "(:facultyId IS NULL OR d.faculty.id = :facultyId) AND " +
            "(:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Department> findByFilters(@Param("facultyId") Long facultyId,
                                   @Param("name") String name,
                                   Pageable pageable);

    @Query("SELECT d, d.faculty.name FROM Department d")
    List<Object[]> findAllWithFacultyName();
}
