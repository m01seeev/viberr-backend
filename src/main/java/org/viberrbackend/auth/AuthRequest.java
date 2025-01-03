package org.viberrbackend.Auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String usernameOrEmail;
    private String password;
}
