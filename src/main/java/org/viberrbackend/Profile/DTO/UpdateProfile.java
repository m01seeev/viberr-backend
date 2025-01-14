package org.viberrbackend.Profile.DTO;

import lombok.Data;

@Data
public class UpdateProfile {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private String picRef;
    private String isDeleted;
}
