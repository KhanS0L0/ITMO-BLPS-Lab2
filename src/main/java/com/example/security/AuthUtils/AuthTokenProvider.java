package com.example.security.AuthUtils;

import com.example.security.BasicAuthUser.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Component
public class AuthTokenProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthTokenProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication getAuthentication(String token) throws UsernameNotFoundException {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        if(passwordEncoder.matches(getPassword(token), userDetails.getPassword()))
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        return null;
    }

    public String getUsername(String token){
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        return new String(decodedBytes).split(":")[0];
    }

    public String getPassword(String token){
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        return new String(decodedBytes).split(":")[1];
    }

    public Long getUserId(String token){
        AuthUser user = (AuthUser) this.userDetailsService.loadUserByUsername(getUsername(token));
        return user.getUserId();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Basic ")){
            return bearerToken.substring(6);
        }
        return null;
    }

}
