package ru.cofffeen.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.cofffeen.model.Account;
import ru.cofffeen.dto.response.AccountResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountDtoMapper {

    private final OperationDtoMapper operationDtoMapper;

    public AccountResponse toResponse(Account domain) {
        if (domain == null) return null;

        return AccountResponse.builder()
                .id(domain.getId())
                .balance(domain.getBalance())
                .ownerId(domain.getOwner() != null ? domain.getOwner().getId() : null)
                .ownerName(domain.getOwner() != null ? domain.getOwner().getName() : null)
                .recentOperations(domain.getOperations() != null ?
                        domain.getOperations().stream()
                                .limit(50)
                                .map(operationDtoMapper::toResponse)
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
                .build();
    }

    public List<AccountResponse> toResponseList(List<Account> domains) {
        if (domains == null) return List.of();
        return domains.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}