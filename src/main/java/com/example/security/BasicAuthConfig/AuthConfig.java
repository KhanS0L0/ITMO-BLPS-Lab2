package com.example.security.BasicAuthConfig;

import com.example.security.AuthUtils.AuthTokenFilter;
import com.example.security.AuthUtils.AuthTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthTokenProvider authTokenProvider;

    public AuthConfig(AuthTokenProvider authTokenProvider) {
        this.authTokenProvider = authTokenProvider;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthTokenFilter authTokenFilter = new AuthTokenFilter(authTokenProvider);
        httpSecurity.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
