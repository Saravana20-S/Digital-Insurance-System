package com.insurance.auth.config;

import com.insurance.auth.entity.Role;
import com.insurance.auth.entity.User;
import com.insurance.auth.repository.UserRepository;
import com.insurance.auth.service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GoogleOAuth2SuccessHandler
        implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauth2User =
                (OAuth2User) authentication.getPrincipal();

        String email =
                oauth2User.getAttribute("email");

        if (email == null || email.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Google account email not available"
            );
            return;
        }

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseGet(() -> {

                            User newUser = User.builder()
                                    .email(email)

                                    /*
                                     * Google handles the real password
                                     * authentication.
                                     *
                                     * We still need a non-null DB value
                                     * because the User entity requires it.
                                     */
                                    .password(
                                            passwordEncoder.encode(
                                                    UUID.randomUUID().toString()
                                            )
                                    )

                                    .role(Role.CUSTOMER)
                                    .active(true)
                                    .build();

                            return userRepository.save(newUser);
                        });

        if (!Boolean.TRUE.equals(user.getActive())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "User account is inactive"
            );

            return;
        }

        String jwt = jwtService.generateToken(user);

        /*
         * For local/demo purposes we return the JWT directly.
         *
         * In a production frontend application, redirect to the
         * frontend and transfer the token using a secure mechanism.
         */
        response.setContentType("application/json");

        response.getWriter().write(
                """
                {
                    "message": "Google login successful",
                    "token": "%s",
                    "userId": %d,
                    "email": "%s",
                    "role": "%s"
                }
                """.formatted(
                        jwt,
                        user.getId(),
                        user.getEmail(),
                        user.getRole().name()
                )
        );
    }
}