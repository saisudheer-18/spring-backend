package springboot.service;

import java.util.List;
import springboot.dto.EmployeeRequest;
import springboot.model.Employee;
import springboot.model.User;

public interface EmployeeService {

    Employee createEmployee(EmployeeRequest request);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee getEmployeeByUser(User user);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);
}
