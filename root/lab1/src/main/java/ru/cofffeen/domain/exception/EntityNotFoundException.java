package ru.cofffeen.domain.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(UUID id) {
        super("Entity with id " + id + " not found");
    }
}