package ru.cofffeen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cofffeen.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);

    boolean existsByLogin(String login);

    @Query("SELECT DISTINCT u FROM UserEntity u LEFT JOIN FETCH u.accounts WHERE u.id = :id")
    Optional<UserEntity> findByIdWithAccounts(@Param("id") Long id);

    @Query("SELECT DISTINCT u FROM UserEntity u LEFT JOIN FETCH u.friends WHERE u.id = :id")
    Optional<UserEntity> findByIdWithFriends(@Param("id") Long id);

    @Query("SELECT DISTINCT u FROM UserEntity u LEFT JOIN FETCH u.accounts LEFT JOIN FETCH u.friends " +
            "WHERE (:gender IS NULL OR u.gender = :gender) " +
            "AND (:hairColor IS NULL OR u.hairColor = :hairColor)")
    Set<UserEntity> findAllWithFilters(@Param("gender") String gender,
                                       @Param("hairColor") String hairColor);
}