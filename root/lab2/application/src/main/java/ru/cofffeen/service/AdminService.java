package ru.cofffeen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cofffeen.exception.BusinessException;
import ru.cofffeen.model.Admin;
import ru.cofffeen.port.AdminRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepositoryPort adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Admin createAdmin(String login, String password, String name) {
        if (adminRepository.existsByLogin(login)) {
            throw new BusinessException("Admin with login " + login + " already exists");
        }

        Admin admin = Admin.builder()
                .login(login)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();

        return adminRepository.save(admin);
    }

    @Transactional(readOnly = true)
    public Admin findByLogin(String login) {
        return adminRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("Admin not found"));
    }
}
