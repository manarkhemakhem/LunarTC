package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;

@Service  // Annotation Spring pour indiquer qu'il s'agit d'un service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;  // Injection du repository

    // Récupérer tous les employés
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Récupérer un employé par ID
    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    // Créer un employé
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Mettre à jour un employé
    public Employee updateEmployee(String id, Employee updatedEmployee) {
        updatedEmployee.setId(id);  // S'assurer que l'ID est bien mis à jour
        return employeeRepository.save(updatedEmployee);
    }

    // Supprimer un employé
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    // Recherche d'employés par nom ou email
    public List<Employee> searchEmployees(String query) {
        return employeeRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }
}
