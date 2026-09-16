package com.insurance.auth.dto.auth;

import com.insurance.auth.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeResponse {

    private Long id;
    private String email;
    private Role role;
    private Boolean active;
}