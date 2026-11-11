package ru.cofffeen.service;

import ru.cofffeen.domain.exception.EntityNotFoundException;
import ru.cofffeen.repository.Repository;

import java.util.List;
import java.util.UUID;

public abstract class BaseService<T> {
    protected Repository<T, UUID> repository;

     public BaseService(Repository<T, UUID> repository) {
        this.repository = repository;
     }

    protected BaseService() {
    }

    public T getById(UUID id) {
        var entity = repository.getById(id);

        if (entity == null) {
            throw new EntityNotFoundException(id);
        }

        return entity;
    }

    public void remove(UUID id) {
        var entity = repository.getById(id);
        repository.remove(entity);
    }

    public List<T> getAll() {
        return repository.getAll();
    }
}
