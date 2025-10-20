package com.XenaOz45.ApiUniversityMS.Repositories;

import com.XenaOz45.ApiUniversityMS.Entities.Role;
import com.XenaOz45.ApiUniversityMS.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySlug(String slug);
    boolean existsByEmail(String email);
    boolean existsBySlug(String slug);

    Page<User> findByFirstNameContainingIgnoreCase(String name, Pageable pageable);
    Page<User> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
    Page<User> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName,
                                                                                  String lastName, Pageable pageable);

    Page<User> findAllByOrderByLastNameAsc(Pageable pageable);
    Page<User> findByRole(Role role, Pageable pageable);

    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<User> searchUsers(@Param("query") String query, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM User u WHERE u.id = :userId AND u.role = :role")
    boolean hasUserRole(@Param("userId") Long userId, @Param("role") Role role);

    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();

}
