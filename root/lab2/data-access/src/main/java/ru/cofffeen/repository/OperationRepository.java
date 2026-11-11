package ru.cofffeen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cofffeen.entity.OperationEntity;
import ru.cofffeen.entity.OperationType;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    List<OperationEntity> findByAccountIdOrderByTimestampDesc(Long accountId);

    @Query("SELECT o FROM OperationEntity o WHERE " +
            "(:type IS NULL OR o.type = :type) AND " +
            "(:accountId IS NULL OR o.account.id = :accountId) " +
            "ORDER BY o.timestamp DESC")
    List<OperationEntity> findAllWithFilters(@Param("type") OperationType type,
                                             @Param("accountId") Long accountId);
}