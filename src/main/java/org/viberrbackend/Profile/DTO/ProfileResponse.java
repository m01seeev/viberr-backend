package org.viberrbackend.Profile.DTO;

import lombok.Data;

@Data
public class ProfileResponse {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String picRef;
    private String bio;
    private String id;
}
