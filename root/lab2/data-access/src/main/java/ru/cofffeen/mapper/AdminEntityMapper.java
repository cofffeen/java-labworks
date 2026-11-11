package ru.cofffeen.mapper;

import ru.cofffeen.entity.AdminEntity;
import ru.cofffeen.model.Admin;

public class AdminEntityMapper {
    public AdminEntity toEntity(Admin domain) {
        if (domain == null) return null;

        AdminEntity entity = new AdminEntity();
        entity.setId(domain.getId());
        entity.setLogin(domain.getLogin());
        entity.setPassword(domain.getPassword());
        entity.setName(domain.getName());

        return entity;
    }

    public Admin toDomain(AdminEntity entity) {
        if (entity == null) return null;

        return Admin.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .name(entity.getName())
                .build();
    }
}
