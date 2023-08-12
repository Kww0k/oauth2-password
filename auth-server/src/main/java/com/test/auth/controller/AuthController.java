package com.test.auth.controller;

import com.test.auth.entity.ResponseResult;
import com.test.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 登陆
    @PostMapping("/token")
    public ResponseResult token(Principal principal, @RequestParam Map<String, String> parameters) {
        return authService.token(principal, parameters);
    }

    // 退出
    @GetMapping("/logout")
    public ResponseResult logout() {
        return authService.logout();
    }
}
