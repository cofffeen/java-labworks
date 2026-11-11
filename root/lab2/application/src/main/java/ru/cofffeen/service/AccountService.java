package ru.cofffeen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cofffeen.exception.BusinessException;
import ru.cofffeen.exception.NotFoundException;
import ru.cofffeen.model.Account;
import ru.cofffeen.model.Operation;
import ru.cofffeen.model.User;
import ru.cofffeen.port.AccountRepositoryPort;
import ru.cofffeen.port.OperationRepositoryPort;
import ru.cofffeen.port.UserRepositoryPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepositoryPort accountRepository;
    private final UserRepositoryPort userRepository;
    private final OperationRepositoryPort operationRepository;
    private final TransferService transferService;

    @Transactional
    public Account createAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Account account = Account.builder()
                .owner(user)
                .balance(BigDecimal.ZERO)
                .build();

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account getAccount(Long accountId) {
        return accountRepository.findByIdWithOwnerAndOperations(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id " + accountId + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Account> getUserAccounts(Long userId) {
        return accountRepository.findByOwnerId(userId);
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAllWithOwner();
    }

    @Transactional
    public void deposit(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Amount must be positive");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.update(account);

        Operation operation = Operation.builder()
                .type("DEPOSIT")
                .amount(amount)
                .commission(BigDecimal.ZERO)
                .timestamp(LocalDateTime.now())
                .account(account)
                .build();

        operationRepository.save(operation);
    }

    @Transactional
    public void withdraw(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Amount must be positive");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.update(account);

        Operation operation = Operation.builder()
                .type("WITHDRAW")
                .amount(amount)
                .commission(BigDecimal.ZERO)
                .timestamp(LocalDateTime.now())
                .account(account)
                .build();

        operationRepository.save(operation);
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        return account.getBalance();
    }

    @Transactional(readOnly = true)
    public List<Operation> getOperations(String type, Long accountId) {
        return operationRepository.findAllWithFilters(type, accountId);
    }
}