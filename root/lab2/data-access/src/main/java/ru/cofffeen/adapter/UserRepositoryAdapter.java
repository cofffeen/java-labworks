package ru.cofffeen.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.cofffeen.entity.UserEntity;
import ru.cofffeen.mapper.AccountEntityMapper;
import ru.cofffeen.mapper.UserEntityMapper;
import ru.cofffeen.repository.UserRepository;
import ru.cofffeen.model.User;
import ru.cofffeen.port.UserRepositoryPort;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final AccountEntityMapper accountEntityMapper;

    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        UserEntity saved = userRepository.save(entity);
        return userEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByIdWithAccounts(Long id) {
        return userRepository.findByIdWithAccounts(id)
                .map(this::mapToDomainWithRelations);
    }

    @Override
    public Optional<User> findByIdWithFriends(Long id) {
        return userRepository.findByIdWithFriends(id)
                .map(this::mapToDomainWithRelations);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(userEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllWithFilters(String gender, String hairColor) {
        return userRepository.findAllWithFilters(gender, hairColor).stream()
                .map(this::mapToDomainWithRelations)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    @Transactional
    public void addFriend(Long userId, Long friendId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found"));

        user.getFriends().add(friend);
        friend.getFriends().add(user);

        userRepository.save(user);
        userRepository.save(friend);
    }

    private User mapToDomainWithRelations(UserEntity entity) {
        User user = userEntityMapper.toDomain(entity);

        if (entity.getFriends() != null) {
            user.setFriends(entity.getFriends().stream()
                    .map(userEntityMapper::toDomain)
                    .collect(Collectors.toList()));
        }

        if (entity.getAccounts() != null) {
            user.setAccounts(entity.getAccounts().stream()
                    .map(accountEntityMapper::toDomain)
                    .collect(Collectors.toList()));
        }

        return user;
    }

    @Override
    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found"));

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        userRepository.save(user);
        userRepository.save(friend);
    }
}