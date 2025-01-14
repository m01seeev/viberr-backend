package org.viberrbackend.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.viberrbackend.Auth.RegisterRequest;
import org.viberrbackend.Profile.DTO.ProfileResponse;
import org.viberrbackend.Profile.DTO.UpdateProfile;
import org.viberrbackend.Profile.ProfileModel;
import org.viberrbackend.Profile.ProfileRepository;
import org.viberrbackend.Profile.ProfileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    public List<ProfileResponse> getContacts(String userID) {
        UserModel user = userRepository.findById(userID).orElseThrow();
        List<ProfileResponse> contacts = new ArrayList<>();
        List<UserModel> userContacts = userRepository.findALlByIdIn(user.getContactsIds());
        for (UserModel userContact : userContacts) {
            ProfileModel contactProfile = profileRepository.findByUserId(userContact.getId());
            ProfileResponse contactResponse = getProfileResponse(userContact, contactProfile);
            contacts.add(contactResponse);
        }
        return contacts;
    }

    private static ProfileResponse getProfileResponse(UserModel userContact, ProfileModel contactProfile) {
        ProfileResponse contactResponse = new ProfileResponse();
        contactResponse.setFirstName(contactProfile.getFirstName());
        contactResponse.setLastName(contactProfile.getLastName());
        contactResponse.setEmail(userContact.getEmail());
        contactResponse.setUsername(userContact.getUsername());
        contactResponse.setPicRef(contactProfile.getPicRef());
        contactResponse.setBio(contactProfile.getBio());
        contactResponse.setId(userContact.getId());
        return contactResponse;
    }

    public String addContact(String userId, UpdateProfile userDto) {
        String username = userDto.getUsername();
        String email = userDto.getEmail();
        String name = (username != null) ? username : email;
        UserModel user = userRepository.findById(userId).orElseThrow();
        List<String> contacts = user.getContactsIds();
        Optional<UserModel> contact = userRepository.findByUsernameOrEmail(name, name);

        if (contact.isPresent()) {
            String contactId = contact.get().getId();
            if (Objects.equals(contact.get().getUsername(), user.getUsername())) {
                return "Это ваши данные";
            }
            if (!contacts.contains(contactId)) {
                contacts.add(contactId);
                user.setContactsIds(contacts);
                userRepository.save(user);
                return "Контакт добавлен";
            } else {
                return "Пользователь уже у вас в контактах";
            }
        } else {
            return "Не удалось найти пользователя";
        }
    }

    public void updateUser(String userId, UpdateProfile userDto) {
        UserModel user = userRepository.findById(userId).orElseThrow(null);
        if (user != null) {
            if (userDto.getIsDeleted() != null) {
                user.setIsDeleted(userDto.getIsDeleted());
            }
            if (userDto.getUsername() != null) {
                user.setUsername(userDto.getUsername());
            }
            if (userDto.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
            }
            userRepository.save(user);
        }
    }

    public void registerUser(RegisterRequest registerRequest) {
        UserModel user = new UserModel();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setContactsIds(new ArrayList<>());
        user.setRole("ADMIN");
        user.setIsDeleted("NO");
        userRepository.save(user);
        profileService.addProfile(registerRequest);
    }

    public void checkIfUserExists(String email, String username) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Пользователь с таким email уже существует");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("Такой логин уже занят");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
    }
}
