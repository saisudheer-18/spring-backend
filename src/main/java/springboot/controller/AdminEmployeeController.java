package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springboot.dto.EmployeeRequest;
import springboot.model.Employee;
import springboot.service.EmployeeService;

@RestController
@RequestMapping("/api/admin/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminEmployeeController {

    private final EmployeeService employeeService;

    public AdminEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestBody EmployeeRequest request) {

        Employee saved = employeeService.createEmployee(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
