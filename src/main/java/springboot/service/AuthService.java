package springboot.service;

import springboot.model.User;

public interface AuthService {

    User login(String username, String password);
}
