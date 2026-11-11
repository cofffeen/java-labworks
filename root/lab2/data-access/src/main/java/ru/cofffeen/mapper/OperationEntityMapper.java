package ru.cofffeen.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.cofffeen.entity.OperationEntity;
import ru.cofffeen.entity.OperationType;
import ru.cofffeen.model.Operation;

@Component
@RequiredArgsConstructor
public class OperationEntityMapper {
    private final AccountEntityMapper accountEntityMapper;

    public OperationEntity toEntity(Operation domain) {
        if (domain == null) return null;

        OperationEntity entity = new OperationEntity();
        entity.setId(domain.getId());
        entity.setType(OperationType.valueOf(domain.getType()));
        entity.setAmount(domain.getAmount());
        entity.setCommission(domain.getCommission());
        entity.setTimestamp(domain.getTimestamp());
        if (domain.getAccount() != null) {
            entity.setAccount(accountEntityMapper.toEntity(domain.getAccount()));
        }
        if (domain.getRelatedAccount() != null) {
            entity.setRelatedAccount(accountEntityMapper.toEntity(domain.getRelatedAccount()));
        }

        return entity;
    }

    public Operation toDomain(OperationEntity entity) {
        if (entity == null) return null;

        return Operation.builder()
                .id(entity.getId())
                .type(entity.getType().name())
                .amount(entity.getAmount())
                .commission(entity.getCommission())
                .timestamp(entity.getTimestamp())
                .account(accountEntityMapper.toDomain(entity.getAccount()))
                .relatedAccount(accountEntityMapper.toDomain(entity.getRelatedAccount()))
                .build();
    }
}