package com.insurance.auth.dto.auth;

import com.insurance.auth.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;

    private Long userId;

    private String email;

    private Role role;

    private Boolean active;
}