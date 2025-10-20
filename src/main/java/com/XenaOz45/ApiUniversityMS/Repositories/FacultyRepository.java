package com.XenaOz45.ApiUniversityMS.Repositories;

import com.XenaOz45.ApiUniversityMS.Entities.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    boolean existsByName(String facultyName);
    boolean existsByShortName(String facultyShortName);
    boolean existsBySlug(String facultySlug);

    Optional<Faculty> findByName(String facultyName);
    Optional<Faculty> findByShortName(String facultyShortName);
    Optional<Faculty> findBySlug(String facultySlug);

    Page<Faculty> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT f, COUNT(d) FROM Faculty f LEFT JOIN f.departments d GROUP BY f")
    Page<Object[]> findFacultiesWithDepartmentCount(Pageable pageable);

    @Query("""
        SELECT f, COALESCE(COUNT(s), 0) AS studentCount
        FROM Faculty f
        LEFT JOIN f.departments d
        LEFT JOIN d.groups g
        LEFT JOIN g.students s
        GROUP BY f
    """)
    Page<Object[]> findFacultiesWithStudentCount(Pageable pageable);

    @Query("SELECT f.name, COUNT(DISTINCT d), COUNT(DISTINCT t), COUNT(DISTINCT s) " +
            "FROM Faculty f " +
            "LEFT JOIN f.departments d " +
            "LEFT JOIN d.teachers t " +
            "LEFT JOIN d.groups g " +
            "LEFT JOIN g.students s " +
            "GROUP BY f.id, f.name")
    List<Object[]> findFacultiesStatistics();

    List<Faculty> findAllByOrderByNameAsc();
}

