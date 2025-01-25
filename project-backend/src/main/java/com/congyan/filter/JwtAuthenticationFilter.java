package com.congyan.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.congyan.entity.RestBean;
import com.congyan.utils.Const;
import com.congyan.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 用于对请求头中Jwt令牌进行校验的工具，为当前请求添加用户验证信息
 * 并将用户的ID存放在请求对象属性中，方便后续使用
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    JwtUtils utils;

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/auth/login",
            "/api/auth/logout",
            "/api/auth/register",
            "/api/auth/ask-code",
            "/api/auth/reset-confirm",
            "/api/auth/reset-password"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        // 如果请求路径在排除列表中，直接放行
        if (EXCLUDED_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorization = request.getHeader("Authorization");
        //log.info(authorization);
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if(jwt != null) {
            UserDetails user = utils.toUser(jwt);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute(Const.ATTR_USER_ID, utils.toId(jwt));
            log.info("JWT 验证通过，用户 ID: {}", utils.toId(jwt));
            filterChain.doFilter(request, response);
        }
        else {
            log.warn("请求未携带有效的 JWT，路径: {}", request.getRequestURI());
            response.getWriter().write(RestBean.unauthorized("no auth").asJsonString());
        }

    }
}
