package ru.cofffeen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.cofffeen.dto.request.CreateAdminRequest;
import ru.cofffeen.dto.request.CreateUserRequest;
import ru.cofffeen.dto.response.AccountResponse;
import ru.cofffeen.dto.response.OperationResponse;
import ru.cofffeen.dto.response.UserResponse;
import ru.cofffeen.dto.mapper.AccountDtoMapper;
import ru.cofffeen.dto.mapper.OperationDtoMapper;
import ru.cofffeen.dto.mapper.UserDtoMapper;
import ru.cofffeen.exception.ErrorResponse;
import ru.cofffeen.model.Account;
import ru.cofffeen.model.Admin;
import ru.cofffeen.model.User;
import ru.cofffeen.service.AccountService;
import ru.cofffeen.service.AdminService;
import ru.cofffeen.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "API for admin operations")
public class AdminController {
    private final UserService userService;
    private final AdminService adminService;
    private final AccountService accountService;
    private final UserDtoMapper userDtoMapper;
    private final AccountDtoMapper accountDtoMapper;
    private final OperationDtoMapper operationDtoMapper;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", description = "Admin creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userDtoMapper.toDomain(request);
        User createdUser = userService.createUser(user);
        return userDtoMapper.toResponse(createdUser);
    }

    @PostMapping("/admins")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new admin", description = "Admin creates another admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public Map<String, String> createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        Admin admin = adminService.createAdmin(
                request.getLogin(),
                request.getPassword(),
                request.getName()
        );
        return Map.of("message", "Admin created successfully", "id", admin.getId().toString());
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users with filters",
            description = "Returns list of all users, optionally filtered by gender and hair color")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public List<UserResponse> getAllUsers(
            @Parameter(description = "Filter by gender", example = "MALE")
            @RequestParam(required = false) String gender,
            @Parameter(description = "Filter by hair color", example = "Brown")
            @RequestParam(required = false) String hairColor) {
        List<User> users = userService.getAllUsers(gender, hairColor);
        return userDtoMapper.toResponseList(users);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID", description = "Returns user information by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponse getUser(@Parameter(description = "User ID", example = "1")
                                @PathVariable Long id) {
        User user = userService.getUser(id);
        return userDtoMapper.toResponse(user);
    }

    @GetMapping("/accounts")
    @Operation(summary = "Get all accounts", description = "Returns list of all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    })
    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return accountDtoMapper.toResponseList(accounts);
    }

    @GetMapping("/users/{userId}/accounts")
    @Operation(summary = "Get user accounts", description = "Returns list of accounts for specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    })
    public List<AccountResponse> getUserAccounts(@Parameter(description = "User ID", example = "1")
                                                 @PathVariable Long userId) {
        List<Account> accounts = accountService.getUserAccounts(userId);
        return accountDtoMapper.toResponseList(accounts);
    }

    @GetMapping("/accounts/{id}")
    @Operation(summary = "Get account by ID", description = "Returns account information with operations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public AccountResponse getAccount(@Parameter(description = "Account ID", example = "1")
                                      @PathVariable Long id) {
        Account account = accountService.getAccount(id);
        return accountDtoMapper.toResponse(account);
    }

    @GetMapping("/accounts/{id}/operations")
    @Operation(summary = "Get account operations", description = "Returns operations for specified account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operations retrieved successfully")
    })
    public List<OperationResponse> getAccountOperations(
            @Parameter(description = "Account ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Filter by operation type")
            @RequestParam(required = false) String type) {
        var operations = accountService.getOperations(type, id);
        return operationDtoMapper.toResponseList(operations);
    }
}