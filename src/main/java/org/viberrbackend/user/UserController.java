package org.viberrbackend.User;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.viberrbackend.Profile.DTO.ProfileResponse;
import org.viberrbackend.Profile.ProfileService;
import org.viberrbackend.Profile.DTO.UpdateProfile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProfileService profileService;

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable String userId, @RequestBody UpdateProfile profileDto) {
        profileService.updateProfile(userId, profileDto);
        userService.updateUser(userId, profileDto);
    }

    @GetMapping("/contacts/{id}")
    public List<ProfileResponse> getContacts(@PathVariable String id) {
        return userService.getContacts(id);
    }

    @PostMapping("/addContact/{userId}")
    public String addContact(@PathVariable String userId, @RequestBody UpdateProfile username) {
        return userService.addContact(userId, username);
    }
}
