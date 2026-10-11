package com.gamelog.gamelog;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("GET")) {
            return true;
        }
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new BizException(401, "未登录");
        }
        try {
            Long userId = JwtUtil.parse(auth.substring(7));
            request.setAttribute("userId", userId);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new BizException(401, "登录已过期，请重新登录");
        }
    }
}