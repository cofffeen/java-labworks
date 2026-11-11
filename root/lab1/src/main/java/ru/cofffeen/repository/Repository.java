package ru.cofffeen.repository;

import java.util.List;

public interface Repository<T, ID> {
    public T add(T entity, ID id);

    public T update(T entity);

    public void remove(T entity);

    public T getById(ID id);

    public List<T> getAll();
}
