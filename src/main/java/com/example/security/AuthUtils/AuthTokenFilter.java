package com.example.security.AuthUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthTokenFilter extends GenericFilterBean {

    private final AuthTokenProvider authTokenProvider;

    public AuthTokenFilter(AuthTokenProvider authTokenProvider) {
        this.authTokenProvider = authTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        String token = authTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        if(token != null){
            Authentication authentication = authTokenProvider.getAuthentication(token);
            if(authentication != null){
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }
            servletRequest.setAttribute("username", authTokenProvider.getUsername(token));
            servletRequest.setAttribute("userId", authTokenProvider.getUserId(token));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
