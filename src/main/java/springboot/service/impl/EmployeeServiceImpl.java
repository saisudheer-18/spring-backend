package springboot.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import springboot.dto.EmployeeRequest;
import springboot.model.Employee;
import springboot.model.Role;
import springboot.model.User;
import springboot.repository.EmployeeRepository;
import springboot.repository.UserRepository;
import springboot.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔥 FIXED: CREATE USER + EMPLOYEE
    @Override
    public Employee createEmployee(EmployeeRequest request) {

        // 1️⃣ Create login account for employee
        User user = new User();
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode("employee123")); // default password
        user.setRole(Role.EMPLOYEE);
        user.setFirstLogin(true);

        User savedUser = userRepository.save(user);

        // 2️⃣ Create employee
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());

        // 🔥 THIS WAS MISSING
        employee.setUser(savedUser);

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public Employee getEmployeeByUser(User user) {
        return employeeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = getEmployeeById(id);

        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmail(employee.getEmail());
        existing.setDepartment(employee.getDepartment());
        existing.setDesignation(employee.getDesignation());

        return employeeRepository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
