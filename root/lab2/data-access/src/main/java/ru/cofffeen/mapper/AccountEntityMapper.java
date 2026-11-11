package ru.cofffeen.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.cofffeen.entity.AccountEntity;
import ru.cofffeen.model.Account;

@Component
@RequiredArgsConstructor
public class AccountEntityMapper {
    private final UserEntityMapper userEntityMapper;

    public AccountEntity toEntity(Account domain) {
        if (domain == null) return null;

        AccountEntity entity = new AccountEntity();
        entity.setId(domain.getId());
        entity.setBalance(domain.getBalance());
        if (domain.getOwner() != null) {
            entity.setOwner(userEntityMapper.toEntity(domain.getOwner()));
        }

        return entity;
    }

    public Account toDomain(AccountEntity entity) {
        if (entity == null) return null;

        return Account.builder()
                .id(entity.getId())
                .balance(entity.getBalance())
                .owner(userEntityMapper.toDomain(entity.getOwner()))
                .build();
    }
}