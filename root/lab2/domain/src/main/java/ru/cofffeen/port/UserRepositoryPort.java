package ru.cofffeen.port;

import ru.cofffeen.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByIdWithAccounts(Long id);
    Optional<User> findByIdWithFriends(Long id);
    Optional<User> findByLogin(String login);
    List<User> findAll();
    List<User> findAllWithFilters(String gender, String hairColor);
    boolean existsByLogin(String login);
    void addFriend(Long userId, Long friendId);
    void removeFriend(Long userId, Long friendId);
}
