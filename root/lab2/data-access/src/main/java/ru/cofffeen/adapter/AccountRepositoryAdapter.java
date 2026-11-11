package ru.cofffeen.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.cofffeen.entity.AccountEntity;
import ru.cofffeen.mapper.AccountEntityMapper;
import ru.cofffeen.repository.AccountRepository;
import ru.cofffeen.model.Account;
import ru.cofffeen.port.AccountRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {
    private final AccountRepository accountRepository;
    private final AccountEntityMapper accountEntityMapper;

    @Override
    @Transactional
    public Account save(Account account) {
        AccountEntity entity = accountEntityMapper.toEntity(account);
        AccountEntity saved = accountRepository.save(entity);
        return accountEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id)
                .map(accountEntityMapper::toDomain);
    }

    @Override
    public Optional<Account> findByIdWithOwnerAndOperations(Long id) {
        return accountRepository.findByIdWithOwnerAndOperations(id)
                .map(accountEntityMapper::toDomain);
    }

    @Override
    public List<Account> findByOwnerId(Long ownerId) {
        return accountRepository.findByOwnerId(ownerId).stream()
                .map(accountEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll().stream()
                .map(accountEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllWithOwner() {
        return accountRepository.findAllWithOwner().stream()
                .map(accountEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Account update(Account account) {
        AccountEntity entity = accountEntityMapper.toEntity(account);
        AccountEntity updated = accountRepository.save(entity);
        return accountEntityMapper.toDomain(updated);
    }
}