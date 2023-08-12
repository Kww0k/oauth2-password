package com.test.auth.service;

import com.test.auth.entity.ResponseResult;

import java.security.Principal;
import java.util.Map;

public interface AuthService {
    ResponseResult token(Principal principal, Map<String, String> parameters);

    ResponseResult logout();
}
