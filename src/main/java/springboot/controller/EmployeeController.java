package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import springboot.dto.ChangePasswordRequest;
import springboot.model.Employee;
import springboot.model.User;
import springboot.repository.UserRepository;
import springboot.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeController(EmployeeService employeeService,
                              UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===================== GET =====================
    // Employee views OWN profile (username from JWT)
    @GetMapping("/me")
    public ResponseEntity<Employee> getMyProfile() {

        String username = getLoggedInUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee employee = employeeService.getEmployeeByUser(user);
        return ResponseEntity.ok(employee);
    }

    // ===================== PATCH =====================
    // Employee changes OWN password
    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request) {

        String username = getLoggedInUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify old password
        if (!passwordEncoder.matches(
                request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        // Encode & save new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setFirstLogin(false);

        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }

    // ===================== HELPER =====================
    private String getLoggedInUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
