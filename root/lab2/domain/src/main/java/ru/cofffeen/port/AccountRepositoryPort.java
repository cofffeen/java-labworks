package ru.cofffeen.port;

import ru.cofffeen.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {
    Account save(Account account);
    Optional<Account> findById(Long id);
    Optional<Account> findByIdWithOwnerAndOperations(Long id);
    List<Account> findByOwnerId(Long ownerId);
    List<Account> findAll();
    List<Account> findAllWithOwner();
    Account update(Account account);
}
