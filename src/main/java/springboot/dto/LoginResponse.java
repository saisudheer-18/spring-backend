package springboot.dto;

import springboot.model.Role;

public class LoginResponse {

    private String token;
    private String username;
    private String role;
    private boolean firstLogin;

    public LoginResponse(String token, String username, Role role, boolean firstLogin) {
        this.token = token;
        this.username = username;
        this.role = role.name(); // ADMIN / EMPLOYEE
        this.firstLogin = firstLogin;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public boolean isFirstLogin() { return firstLogin; }
}
