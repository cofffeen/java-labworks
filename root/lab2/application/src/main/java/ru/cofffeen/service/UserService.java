package ru.cofffeen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cofffeen.exception.BusinessException;
import ru.cofffeen.exception.NotFoundException;
import ru.cofffeen.model.User;
import ru.cofffeen.port.UserRepositoryPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new BusinessException("User with login " + user.getLogin() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findByIdWithAccounts(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User with login " + login + " not found"));
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(String gender, String hairColor) {
        return userRepository.findAllWithFilters(gender, hairColor);
    }

    @Transactional
    public User addFriend(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new BusinessException("Cannot add yourself as friend");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("Friend not found"));

        if (user.getFriends() != null && user.getFriends().stream()
                .anyMatch(f -> f.getId().equals(friendId))) {
            throw new BusinessException("Users are already friends");
        }

        userRepository.addFriend(userId, friendId);

        return userRepository.findByIdWithFriends(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public List<User> getUserFriends(Long userId) {
        User user = userRepository.findByIdWithFriends(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return user.getFriends();
    }

    public boolean areFriends(Long userId1, Long userId2) {
        User user = userRepository.findByIdWithFriends(userId1)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return user.getFriends() != null && user.getFriends().stream()
                .anyMatch(friend -> friend.getId().equals(userId2));
    }

    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new BusinessException("Cannot remove yourself");
        }

        User user = userRepository.findByIdWithFriends(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        boolean isFriend = user.getFriends() != null && user.getFriends().stream()
                .anyMatch(f -> f.getId().equals(friendId));

        if (!isFriend) {
            throw new BusinessException("Users are not friends");
        }

        userRepository.removeFriend(userId, friendId);
    }
}