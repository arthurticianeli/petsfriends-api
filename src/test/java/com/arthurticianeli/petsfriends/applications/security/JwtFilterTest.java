package com.arthurticianeli.petsfriends.security;

import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;
import com.arthurticianeli.petsfriends.applications.authentication.service.JwtService;
import com.arthurticianeli.petsfriends.applications.authentication.domain.services.IUserService;
import com.arthurticianeli.petsfriends.exceptions.InvalidTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtFilterTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private IUserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateUserWithValidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(jwtService.getEmailFromToken("validToken")).thenReturn("user@example.com");

        User mockUser = new User();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("password");

        when(userService.getByEmail("user@example.com")).thenReturn(mockUser);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldThrowExceptionIfTokenIsInvalid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(jwtService.getEmailFromToken("validToken")).thenThrow(new InvalidTokenException("Invalid token"));

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldThrowIfAnyExceptionOccurs() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(userService.getByEmail("email.com")).thenThrow(new RuntimeException("Some runtime exception"));

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }


        @Test
    void shouldNotAuthenticateUserWithInvalidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        doThrow(new InvalidTokenException("Invalid token")).when(jwtService).getEmailFromToken("invalidToken");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldProceedWithoutAuthenticationIfNoTokenProvided() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldNotFilterRequestsToUserEndpoint() throws Exception {
        when(request.getRequestURI()).thenReturn("/v1/user/something");

        assertTrue(jwtFilter.shouldNotFilter(request));
    }

    @Test
    void shouldFilterOtherRequests() throws Exception {
        when(request.getRequestURI()).thenReturn("/v1/pets");

        assertFalse(jwtFilter.shouldNotFilter(request));
    }
}