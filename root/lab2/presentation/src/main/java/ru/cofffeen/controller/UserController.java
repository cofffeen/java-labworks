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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import ru.cofffeen.exception.ErrorResponse;
import ru.cofffeen.model.User;
import ru.cofffeen.security.SecurityService;
import ru.cofffeen.service.UserService;
import ru.cofffeen.dto.request.AddFriendRequest;
import ru.cofffeen.dto.request.CreateUserRequest;
import ru.cofffeen.dto.response.UserResponse;
import ru.cofffeen.dto.mapper.UserDtoMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API for managing users")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final SecurityService securityService;

    @GetMapping("/me")
    @Operation(summary = "Get current user info", description = "Returns information about the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public UserResponse getCurrentUser() {
        String login = securityService.getCurrentUserLogin();
        User user = userService.getUserByLogin(login);
        return userDtoMapper.toResponse(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns user information by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UserResponse getUser(@Parameter(description = "User ID", example = "1")
                                @PathVariable Long id) {
        if (!securityService.isAdmin()) {
            Long currentUserId = securityService.getCurrentUserId();
            if (!currentUserId.equals(id)) {
                throw new AccessDeniedException("You can only view your own profile");
            }
        }

        User user = userService.getUser(id);
        return userDtoMapper.toResponse(user);
    }

    @GetMapping("/me/accounts")
    @Operation(summary = "Get current user accounts", description = "Returns accounts of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    })
    public List<UserResponse> getMyAccounts() {
        Long userId = securityService.getCurrentUserId();
        User user = userService.getUser(userId);
        return List.of(userDtoMapper.toResponse(user));
    }

    @PostMapping("/friends")
    @Operation(summary = "Add friend to current user", description = "Adds a friend relationship for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friend added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or users already friends"),
            @ApiResponse(responseCode = "404", description = "User or friend not found")
    })
    public UserResponse addFriend(@Valid @RequestBody AddFriendRequest request) {
        Long currentUserId = securityService.getCurrentUserId();

        if (!currentUserId.equals(request.getUserId())) {
            throw new AccessDeniedException("You can only add friends to your own account");
        }

        User user = userService.addFriend(currentUserId, request.getFriendId());
        return userDtoMapper.toResponse(user);
    }

    @DeleteMapping("/friends/{friendId}")
    @Operation(summary = "Remove friend from current user", description = "Removes a friend relationship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friend removed successfully"),
            @ApiResponse(responseCode = "400", description = "Users are not friends"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public Map<String, String> removeFriend(@Parameter(description = "Friend ID", example = "2")
                                            @PathVariable Long friendId) {
        Long currentUserId = securityService.getCurrentUserId();
        userService.removeFriend(currentUserId, friendId);
        return Map.of("message", "Friend removed successfully");
    }

    @GetMapping("/me/friends")
    @Operation(summary = "Get current user friends", description = "Returns list of current user's friends")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friends retrieved successfully")
    })
    public List<UserResponse> getMyFriends() {
        Long userId = securityService.getCurrentUserId();
        List<User> friends = userService.getUserFriends(userId);
        return userDtoMapper.toResponseList(friends);
    }
}