package org.viberrbackend.Profile;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "profiles")
public class ProfileModel {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String picRef;
    private String userId;
    private String bio;
}
