package ru.cofffeen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cofffeen.entity.AccountEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByOwnerId(Long ownerId);

    @Query("SELECT DISTINCT a FROM AccountEntity a LEFT JOIN FETCH a.owner LEFT JOIN FETCH a.operations WHERE a.id = :id")
    Optional<AccountEntity> findByIdWithOwnerAndOperations(@Param("id") Long id);

    @Query("SELECT DISTINCT a FROM AccountEntity a LEFT JOIN FETCH a.owner")
    List<AccountEntity> findAllWithOwner();
}