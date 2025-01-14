package org.viberrbackend.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.viberrbackend.Jwt.JwtUtil;
import org.viberrbackend.User.UserModel;
import org.viberrbackend.User.UserRepository;
import org.viberrbackend.User.UserService;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest authRequest) {
        try {
            Optional<UserModel> userOpt = userRepository.findByUsernameOrEmail(authRequest.getUsernameOrEmail(), authRequest.getUsernameOrEmail());
            String username = userOpt.map(UserModel::getUsername).orElse(authRequest.getUsernameOrEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, authRequest.getPassword())
            );
            UserModel user = userOpt.orElseThrow(() -> new Exception("User not found"));
            if (Objects.equals(user.getIsDeleted(), "YES")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Вы заблокированы"));
            }
            String token = jwtUtil.generateToken(username);
            String userId = user.getId();
            return ResponseEntity.ok(new TokenResponse(token, userId, user.getRole()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Неверный логин или пароль"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegisterRequest registerRequest) {
        userService.checkIfUserExists(registerRequest.getEmail(), registerRequest.getUsername());
        userService.registerUser(registerRequest);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }
}
