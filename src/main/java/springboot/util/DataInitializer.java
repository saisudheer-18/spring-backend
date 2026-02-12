package springboot.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import springboot.model.Role;
import springboot.model.User;
import springboot.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createDefaultAdmin(UserRepository userRepository,
                                         PasswordEncoder passwordEncoder) {

        return args -> {

            // Check if admin already exists
            if (!userRepository.existsByUsername("admin")) {

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                admin.setFirstLogin(false);

                userRepository.save(admin);

                System.out.println("✅ Default Admin Created");
            }
        };
    }
}
