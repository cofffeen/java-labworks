package ru.cofffeen.port;

import ru.cofffeen.model.Admin;

import java.util.Optional;

public interface AdminRepositoryPort {
    Admin save(Admin admin);
    Optional<Admin> findByLogin(String login);
    boolean existsByLogin(String login);
}
