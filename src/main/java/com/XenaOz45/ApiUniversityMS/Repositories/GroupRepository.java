package com.XenaOz45.ApiUniversityMS.Repositories;

import com.XenaOz45.ApiUniversityMS.Entities.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByNumber(String number);
    boolean existsBySlug(String slug);

    Optional<Group> findByNumber(String number);
    Optional<Group> findBySlug(String slug);

    List<Group> findAllByDepartmentId(Long departmentId);
    Page<Group> findAllByDepartmentId(Long departmentId, Pageable pageable);

    List<Group> findAllByCuratorId(Long curatorId);

    Page<Group> findByNumberContainingIgnoreCase(String number, Pageable pageable);

    @Query("SELECT g, COUNT(s) FROM Group g LEFT JOIN g.students s GROUP BY g")
    Page<Group> findAllWithCountOfStudents(Pageable pageable);

    @Query("SELECT g FROM Group g WHERE g.department.faculty.id = :facultyId")
    Page<Group> findAllByFacultyId(@Param("facultyId") Long facultyId, Pageable pageable);

    List<Group> findAllByCuratorIsNull();
    Page<Group> findAllByCuratorIsNull(Pageable pageable);

    @Query("SELECT g FROM Group g WHERE " +
            "(:departmentId IS NULL OR g.department.id = :departmentId) AND " +
            "(:facultyId IS NULL OR g.department.faculty.id = :facultyId) AND " +
            "(:search IS NULL OR LOWER(g.number) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Group> findByFilters(@Param("departmentId") Long departmentId,
                              @Param("facultyId") Long facultyId,
                              @Param("search") String search,
                              Pageable pageable);

    @Query("SELECT DISTINCT g FROM Group g WHERE EXISTS " +
            "(SELECT s FROM Student s WHERE s.group = g)")
    List<Group> findActiveGroups();
}
