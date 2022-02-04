package com.wolfhack.driveservice.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfhack.driveservice.utils.AuthorizationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final AuthorizationUtils authorizationUtils = new AuthorizationUtils();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (authorizationUtils.isAuthorizationPage(request)) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationUtils.isTokenHeader(authorizationHeader)) {
                try{
                    DecodedJWT decodedJWT = authorizationUtils.decodeJWT(authorizationHeader);
                    authorizationUtils.putAuthorizationData(decodedJWT);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    Map<String, String> error = authorizationUtils.setError(response, exception);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    private Map<String, String> setError(HttpServletResponse response, Exception exception) {
        return authorizationUtils.setError(response, exception);
    }

    private void putAuthorizationData(DecodedJWT decodedJWT) {
        authorizationUtils.putAuthorizationData(decodedJWT);
    }

    private DecodedJWT decodeJWT(String authorizationHeader) {
        return authorizationUtils.decodeJWT(authorizationHeader);
    }

}
