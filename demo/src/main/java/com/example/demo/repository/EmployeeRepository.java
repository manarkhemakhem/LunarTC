package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}
