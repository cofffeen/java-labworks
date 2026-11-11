package ru.cofffeen.inmemory;

import ru.cofffeen.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRepository<T, ID> implements Repository<T, ID> {
    private List<RepositoryObject<T, ID>> entities = new ArrayList<>();

    @Override
    public T add(T entity, ID id) {
        entities.add(new RepositoryObject<T, ID>(entity, id));

        return entity;
    }

    @Override
    public T update(T entity) {
        var index = findEntity(entity);
        if (index != null) {
            var savedRepositoryObject = entities.get(index);
            savedRepositoryObject.entity = entity;
            entities.set(index, savedRepositoryObject);

            return entity;
        }

        return null;
    }

    @Override
    public void remove(T entity) {
        var index = findEntity(entity);
        if (index == null) {
            return;
        }

        entities.remove(index.intValue());
    }

    @Override
    public T getById(ID id) {
        Optional<RepositoryObject<T, ID>> found = entities.stream()
                .filter(obj -> obj.id.equals(id))
                .findFirst();
        return found.map(obj -> obj.entity).orElse(null);
    }

    @Override
    public List<T> getAll() {
        List<T> all = new ArrayList<>();
        for (var object : entities) {
            all.add(object.entity);
        }

        return all;
    }

    private Integer findEntity(T entity) {
        for (int i = 0; i < entities.size(); ++i) {
            if (entities.get(i).entity == entity) {
                return i;
            }
        }

        return null;
    }
}
