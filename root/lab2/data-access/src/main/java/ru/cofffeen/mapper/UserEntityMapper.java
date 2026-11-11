package ru.cofffeen.mapper;

import org.springframework.stereotype.Component;
import ru.cofffeen.entity.UserEntity;
import ru.cofffeen.model.User;

import java.util.ArrayList;
import java.util.HashSet;

@Component
public class UserEntityMapper {
    public UserEntity toEntity(User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setLogin(domain.getLogin());
        entity.setPassword(domain.getPassword());
        entity.setName(domain.getName());
        entity.setAge(domain.getAge());
        entity.setGender(domain.getGender());
        entity.setHairColor(domain.getHairColor());

        return entity;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .name(entity.getName())
                .age(entity.getAge())
                .gender(entity.getGender())
                .hairColor(entity.getHairColor())
                .build();
    }
}