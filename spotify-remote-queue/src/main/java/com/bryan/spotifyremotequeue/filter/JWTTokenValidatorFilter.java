package com.bryan.spotifyremotequeue.filter;

import com.bryan.spotifyremotequeue.config.security.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@Component
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = extractToken(request.getHeader("Authorization"));
        if (jwt != null) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String username = (String) claims.get("username");
                String roomId = (String) claims.get("roomId");
                Principal principal = new Principal(username, roomId);
                String authorities = (String) claims.get("authorities");
                Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token received");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (request.getServletPath().equals("/api/v1/spotify/registerRoom") ||
                request.getServletPath().equals("/api/v1/spotify/registerUser")) {
            return true;
        }
        return false;
    }

    private String extractToken(String authenticationHeader) {
        if (authenticationHeader == null) {
            return null;
        }
        return authenticationHeader.substring(7);
    }
}
