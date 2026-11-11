package ru.cofffeen.inmemory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepositoryObject<T, U> {
    public T entity;
    public U id;

    public RepositoryObject(T entity, U id) {
        this.entity = entity;
        this.id = id;
    }
}
