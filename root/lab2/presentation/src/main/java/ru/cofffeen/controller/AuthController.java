package ru.cofffeen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {
    @GetMapping("/login")
    @Operation(summary = "Login page", description = "Shows login form")
    public String login() {
        return "login";
    }
}