package ru.cofffeen.service;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cofffeen.exception.BusinessException;
import ru.cofffeen.exception.NotFoundException;
import ru.cofffeen.model.Account;
import ru.cofffeen.model.Operation;
import ru.cofffeen.port.AccountRepositoryPort;
import ru.cofffeen.port.OperationRepositoryPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepositoryPort accountRepository;
    private final OperationRepositoryPort operationRepository;
    private final UserService userService;

    @Transactional
    public TransferResult transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Amount must be positive");
        }

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new NotFoundException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new NotFoundException("Destination account not found"));

        BigDecimal commissionRate = calculateCommissionRate(fromAccount, toAccount);
        BigDecimal commission = amount.multiply(commissionRate)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalWithdraw = amount.add(commission);

        if (fromAccount.getBalance().compareTo(totalWithdraw) < 0) {
            throw new BusinessException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(totalWithdraw));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.update(fromAccount);
        accountRepository.update(toAccount);

        Operation withdrawOp = Operation.builder()
                .type("TRANSFER_OUT")
                .amount(amount)
                .commission(commission)
                .timestamp(LocalDateTime.now())
                .account(fromAccount)
                .relatedAccount(toAccount)
                .build();
        operationRepository.save(withdrawOp);

        Operation depositOp = Operation.builder()
                .type("TRANSFER_IN")
                .amount(amount)
                .commission(BigDecimal.ZERO)
                .timestamp(LocalDateTime.now())
                .account(toAccount)
                .relatedAccount(fromAccount)
                .build();
        operationRepository.save(depositOp);

        return TransferResult.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(amount)
                .commission(commission)
                .build();
    }

    private BigDecimal calculateCommissionRate(Account from, Account to) {
        Long fromUserId = from.getOwner().getId();
        Long toUserId = to.getOwner().getId();

        if (fromUserId.equals(toUserId)) {
            return BigDecimal.ZERO;
        }

        if (userService.areFriends(fromUserId, toUserId)) {
            return new BigDecimal("0.03");
        }

        return new BigDecimal("0.10");
    }

    @Data
    @Builder
    public static class TransferResult {
        private Long fromAccountId;
        private Long toAccountId;
        private BigDecimal amount;
        private BigDecimal commission;
    }
}