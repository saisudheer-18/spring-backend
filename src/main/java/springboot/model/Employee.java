package springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Department is required")
    private String department;

    private String designation;

    // Link to login account
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructors
    public Employee() {}

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }
 
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }
 
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
}
