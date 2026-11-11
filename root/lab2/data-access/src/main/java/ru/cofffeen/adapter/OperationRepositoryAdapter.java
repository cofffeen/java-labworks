package ru.cofffeen.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.cofffeen.entity.OperationType;
import ru.cofffeen.mapper.OperationEntityMapper;
import ru.cofffeen.model.Operation;
import ru.cofffeen.port.OperationRepositoryPort;
import ru.cofffeen.repository.OperationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OperationRepositoryAdapter implements OperationRepositoryPort {
    private final OperationRepository operationRepository;
    private final OperationEntityMapper operationEntityMapper;

    @Override
    @Transactional
    public Operation save(Operation operation) {
        var entity = operationEntityMapper.toEntity(operation);
        var saved = operationRepository.save(entity);
        return operationEntityMapper.toDomain(saved);
    }

    @Override
    public List<Operation> findByAccountId(Long accountId) {
        return operationRepository.findByAccountIdOrderByTimestampDesc(accountId).stream()
                .map(operationEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Operation> findAllWithFilters(String type, Long accountId) {
        OperationType operationType = type != null ? OperationType.valueOf(type) : null;
        return operationRepository.findAllWithFilters(operationType, accountId).stream()
                .map(operationEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}