package ru.cofffeen.port;

import ru.cofffeen.model.Operation;

import java.util.List;

public interface OperationRepositoryPort {
    Operation save(Operation operation);
    List<Operation> findByAccountId(Long accountId);
    List<Operation> findAllWithFilters(String type, Long accountId);
}
