package org.viberrbackend.Auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
