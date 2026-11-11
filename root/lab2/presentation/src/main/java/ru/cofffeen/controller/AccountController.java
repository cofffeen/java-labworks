package ru.cofffeen.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import ru.cofffeen.dto.mapper.AccountDtoMapper;
import ru.cofffeen.dto.mapper.OperationDtoMapper;
import ru.cofffeen.dto.request.DepositWithdrawRequest;
import ru.cofffeen.dto.request.TransferRequest;
import ru.cofffeen.dto.response.AccountResponse;
import ru.cofffeen.dto.response.OperationResponse;
import ru.cofffeen.dto.response.TransferResponse;
import ru.cofffeen.exception.ErrorResponse;
import ru.cofffeen.model.Account;
import ru.cofffeen.model.Operation;
import ru.cofffeen.security.SecurityService;
import ru.cofffeen.service.AccountService;
import ru.cofffeen.service.TransferService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Controller", description = "API for managing accounts")
public class AccountController {
    private final AccountService accountService;
    private final TransferService transferService;
    private final AccountDtoMapper accountDtoMapper;
    private final OperationDtoMapper operationDtoMapper;
    private final SecurityService securityService;

    private void validateAccountAccess(Long accountId) {
        if (!securityService.isAdmin()) {
            Account account = accountService.getAccount(accountId);
            Long currentUserId = securityService.getCurrentUserId();
            if (!account.getOwner().getId().equals(currentUserId)) {
                throw new AccessDeniedException("You can only access your own accounts");
            }
        }
    }

    @GetMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Get account by ID", description = "Returns account information by account ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found",
                    content = @Content(schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public AccountResponse getAccount(@Parameter(description = "Account ID", example = "1")
                                      @PathVariable Long id) {
        validateAccountAccess(id);
        Account account = accountService.getAccount(id);
        return accountDtoMapper.toResponse(account);
    }

    @GetMapping("/my")
    @io.swagger.v3.oas.annotations.Operation(summary = "Get current user accounts", description = "Returns accounts of the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    })
    public List<AccountResponse> getMyAccounts() {
        Long userId = securityService.getCurrentUserId();
        List<Account> accounts = accountService.getUserAccounts(userId);
        return accountDtoMapper.toResponseList(accounts);
    }

    @GetMapping("/{id}/balance")
    @io.swagger.v3.oas.annotations.Operation(summary = "Get account balance", description = "Returns current balance of specified account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public Map<String, BigDecimal> getBalance(@Parameter(description = "Account ID", example = "1")
                                              @PathVariable Long id) {
        validateAccountAccess(id);
        BigDecimal balance = accountService.getBalance(id);
        return Map.of("balance", balance);
    }

    @PostMapping("/deposit")
    @io.swagger.v3.oas.annotations.Operation(summary = "Deposit money", description = "Deposits money to specified account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public Map<String, String> deposit(@Valid @RequestBody DepositWithdrawRequest request) {
        validateAccountAccess(request.getAccountId());
        accountService.deposit(request.getAccountId(), request.getAmount());
        return Map.of("message", "Deposit successful");
    }

    @PostMapping("/withdraw")
    @io.swagger.v3.oas.annotations.Operation(summary = "Withdraw money", description = "Withdraws money from specified account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdrawal successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount or insufficient funds"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public Map<String, String> withdraw(@Valid @RequestBody DepositWithdrawRequest request) {
        validateAccountAccess(request.getAccountId());
        accountService.withdraw(request.getAccountId(), request.getAmount());
        return Map.of("message", "Withdrawal successful");
    }

    @PostMapping("/transfer")
    @io.swagger.v3.oas.annotations.Operation(summary = "Transfer money", description = "Transfers money between accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer successful",
                    content = @Content(schema = @Schema(implementation = TransferResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid amount or insufficient funds"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public TransferResponse transfer(@Valid @RequestBody TransferRequest request) {
        validateAccountAccess(request.getFromAccountId());

        TransferService.TransferResult result = transferService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );

        return TransferResponse.builder()
                .fromAccountId(result.getFromAccountId())
                .toAccountId(result.getToAccountId())
                .amount(result.getAmount())
                .commission(result.getCommission())
                .build();
    }

    @GetMapping("/{id}/operations")
    @io.swagger.v3.oas.annotations.Operation(summary = "Get account operations", description = "Returns operations for specified account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operations retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public List<OperationResponse> getAccountOperations(
            @Parameter(description = "Account ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Filter by operation type")
            @RequestParam(required = false) String type) {
        validateAccountAccess(id);
        List<Operation> operations = accountService.getOperations(type, id);
        return operationDtoMapper.toResponseList(operations);
    }
}