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

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Optional<UserModel> userOpt = userRepository.findByEmail(authRequest.getUsernameOrEmail());
            String username = userOpt.map(UserModel::getUsername).orElse(authRequest.getUsernameOrEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, authRequest.getPassword())
            );
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
