package springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import springboot.model.Employee;
import springboot.model.User;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUser(User user);
}
