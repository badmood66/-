package com.finance.financesystembackend.auth.controller;

import com.finance.financesystembackend.auth.service.AuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest req) {
        String token = authService.login(req.getUsername(), req.getPassword());
        return new LoginResponse(token);
    }

    // DTO
    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    static class LoginResponse {
        private String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }
}
