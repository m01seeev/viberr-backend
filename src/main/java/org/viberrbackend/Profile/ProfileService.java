package org.viberrbackend.Profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viberrbackend.Auth.RegisterRequest;
import org.viberrbackend.Profile.DTO.ProfileResponse;
import org.viberrbackend.Profile.DTO.UpdateProfile;
import org.viberrbackend.User.UserModel;
import org.viberrbackend.User.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileResponse getProfileByUserId(String id) throws Exception {
        ProfileModel profile = profileRepository.findByUserId(id);
        Optional<UserModel> user = userRepository.findById(id);
        ProfileResponse profileDto = new ProfileResponse();
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        profileDto.setPicRef(profile.getPicRef());
        profileDto.setUsername(user.get().getUsername());
        profileDto.setEmail(user.get().getEmail());
        profileDto.setBio(profile.getBio());
        return profileDto;
    }

    public void addProfile(RegisterRequest registerRequest) {
        UserModel user = userRepository.findByUsername(registerRequest.getUsername());
        ProfileModel profile = new ProfileModel();
        profile.setFirstName(registerRequest.getFirstName());
        profile.setLastName(registerRequest.getLastName());
        profile.setPicRef("https://res.cloudinary.com/dtrqjwepw/image/upload/v1736603476/lr6bnyeifjo5eod3zg2m.jpg");
        profile.setUserId(user.getId());
        profile.setBio("");
        profileRepository.save(profile);
    }

    public void updateProfile(String userId, UpdateProfile profileDto) {
        ProfileModel profile = profileRepository.findByUserId(userId);
        if (profile != null) {
            if (profileDto.getFirstName() != null) {
                profile.setFirstName(profileDto.getFirstName());
            }
            if (profileDto.getLastName() != null) {
                profile.setLastName(profileDto.getLastName());
            }
            if (profileDto.getPicRef() != null) {
                profile.setPicRef(profileDto.getPicRef());
            }
            if (profileDto.getBio() != null) {
                profile.setBio(profileDto.getBio());
            }
            profileRepository.save(profile);
        }
    }
}
