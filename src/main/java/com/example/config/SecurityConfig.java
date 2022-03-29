package com.example.config;

import com.example.entity.enums.AccountRoles;
import com.example.security.AuthUtils.AuthTokenProvider;
import com.example.security.BasicAuthConfig.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthTokenProvider authTokenProvider;

    @Autowired
    public SecurityConfig(AuthTokenProvider authTokenProvider) {
        this.authTokenProvider = authTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers("/api/account/signUp").permitAll()

                .antMatchers("/api/published/all").hasRole(AccountRoles.USER.getSecurityRole())
                .antMatchers("/api/temporary/create").hasRole(AccountRoles.USER.getSecurityRole())
                .antMatchers("/api/temporary/update").hasRole(AccountRoles.USER.getSecurityRole())
                .antMatchers("/api/notification/all").hasRole(AccountRoles.USER.getSecurityRole())

                .antMatchers("/api/temporary/all").hasRole(AccountRoles.ADMIN.getSecurityRole())
                .antMatchers("/api/notification/send").hasRole(AccountRoles.ADMIN.getSecurityRole())

                .anyRequest().authenticated()

                .and()
                .apply(new AuthConfig(authTokenProvider));
    }
}