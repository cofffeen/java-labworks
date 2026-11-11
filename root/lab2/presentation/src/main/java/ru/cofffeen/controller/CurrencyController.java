package ru.cofffeen.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import ru.cofffeen.model.Account;
import ru.cofffeen.security.SecurityService;
import ru.cofffeen.service.AccountService;
import ru.cofffeen.service.CurrencyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Currency Controller", description = "API for currency conversion")
public class CurrencyController {
    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final SecurityService securityService;

    @GetMapping("/{id}/operations/export")
    public List<Map<String, Object>> getOperationsInCurrency(
            @PathVariable Long id,
            @RequestParam(defaultValue = "RUB") String currency,
            @RequestParam(required = false) String type) {

        validateAccountAccess(id);
        List<ru.cofffeen.model.Operation> operations = accountService.getOperations(type, id);

        return operations.stream()
                .map(op -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", op.getId());
                    map.put("type", op.getType());
                    map.put("amountRub", op.getAmount());
                    map.put("amountInCurrency", currencyService.convertToCurrency(op.getAmount(), currency));
                    map.put("currency", currency.toUpperCase());
                    map.put("commissionRub", op.getCommission());
                    map.put("timestamp", op.getTimestamp());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private void validateAccountAccess(Long accountId) {
        if (!securityService.isAdmin()) {
            Account account = accountService.getAccount(accountId);
            Long currentUserId = securityService.getCurrentUserId();
            if (!account.getOwner().getId().equals(currentUserId)) {
                throw new AccessDeniedException("You can only access your own accounts");
            }
        }
    }
}