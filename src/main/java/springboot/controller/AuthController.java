package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import springboot.dto.LoginRequest;
import springboot.dto.LoginResponse;
import springboot.model.User;
import springboot.repository.UserRepository;
import springboot.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // 🔐 Password validation
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // ✅ Generate JWT
        String token = jwtUtil.generateToken(user);

        // ✅ PASS Role ENUM (not String)
        LoginResponse response = new LoginResponse(
                token,
                user.getUsername(),
                user.getRole(),          // ✅ FIXED
                user.isFirstLogin()
        );

        return ResponseEntity.ok(response);
    }
}
