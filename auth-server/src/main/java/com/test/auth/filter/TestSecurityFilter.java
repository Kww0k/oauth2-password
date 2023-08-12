package com.test.auth.filter;

import com.alibaba.fastjson.JSON;
import com.test.auth.entity.ResponseResult;
import com.test.auth.enums.AppHttpCodeEnum;
import com.test.auth.utils.JwtUtil;
import com.test.auth.utils.RedisCache;
import com.test.auth.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static com.test.auth.constants.AuthConstants.*;
import static com.test.auth.enums.AppHttpCodeEnum.CXC_AUTH;

@Component
public class TestSecurityFilter extends OncePerRequestFilter {
    private final RedisCache redisCache;

    @Autowired
    public TestSecurityFilter(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 所有请求需要相同的请求头来严重
        String authorization = request.getHeader(AUTH_HEADER);
        String certification = request.getHeader(CERT_HEADER);
        if (!Objects.equals(authorization, AUTH_INFO) || !Objects.equals(certification, CERT_INFO)) {
            ResponseResult result = ResponseResult.errorResult(CXC_AUTH);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        // 放行登陆接口
        if (!Objects.equals(new URL(request.getRequestURL().toString()).getPath(), "/sso/oauth/token")) {
            // 如果不是放行的接口 则需要查询缓存中是否有用户登陆过的信息
            String username;
            try {
                username = (String) JwtUtil.parseJWT(request.getHeader(AUTH_TOKEN).substring(TOKEN_START)).get(AUTH_USER);
            } catch (Exception e) {
                e.printStackTrace();
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.AUTH_EXPIRE);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }
            // 为空则报错
            Object user = redisCache.getCacheObject(LOGIN_KEY + username);
            if (user == null) {
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
