package ru.cofffeen.dto.mapper;

import org.springframework.stereotype.Component;
import ru.cofffeen.model.Account;
import ru.cofffeen.model.User;
import ru.cofffeen.dto.request.CreateUserRequest;
import ru.cofffeen.dto.response.UserResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper {

    public User toDomain(CreateUserRequest request) {
        if (request == null) return null;

        return User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .name(request.getName())
                .age(request.getAge())
                .gender(request.getGender())
                .hairColor(request.getHairColor())
                .build();
    }

    public UserResponse toResponse(User domain) {
        if (domain == null) return null;

        return UserResponse.builder()
                .id(domain.getId())
                .login(domain.getLogin())
                .name(domain.getName())
                .age(domain.getAge())
                .gender(domain.getGender())
                .hairColor(domain.getHairColor())
                .friendIds(domain.getFriends() != null ?
                        domain.getFriends().stream()
                                .map(User::getId)
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
                .accountIds(domain.getAccounts() != null ?
                        domain.getAccounts().stream()
                                .map(Account::getId)
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
                .build();
    }

    public List<UserResponse> toResponseList(List<User> domains) {
        if (domains == null) return List.of();
        return domains.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}