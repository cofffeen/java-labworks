package ru.cofffeen.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.cofffeen.entity.AdminEntity;
import ru.cofffeen.entity.UserEntity;
import ru.cofffeen.mapper.AdminEntityMapper;
import ru.cofffeen.model.Admin;
import ru.cofffeen.model.User;
import ru.cofffeen.port.AdminRepositoryPort;
import ru.cofffeen.repository.AdminRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminRepositoryAdapter implements AdminRepositoryPort {
    private final AdminRepository adminRepository;

    @Override
    public Admin save(Admin admin) {
        AdminEntityMapper adminEntityMapper = new AdminEntityMapper();
        AdminEntity entity = adminEntityMapper.toEntity(admin);
        AdminEntity saved = adminRepository.save(entity);
        return adminEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Admin> findByLogin(String login) {
        AdminEntityMapper adminEntityMapper = new AdminEntityMapper();
        return adminRepository.findByLogin(login)
                .map(adminEntityMapper::toDomain);
    }

    @Override
    public boolean existsByLogin(String login) {
        return adminRepository.existsByLogin(login);
    }

}
