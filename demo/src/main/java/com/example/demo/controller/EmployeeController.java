package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@CrossOrigin(origins = "http://localhost:4200") 
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Récupérer tous les employés
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Récupérer un employé par ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un employé
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        // Si vous souhaitez ajouter une validation ici, faites-le manuellement
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'employé est obligatoire.");
        }
        if (employee.getEmail() == null || !employee.getEmail().contains("@")) {
            throw new IllegalArgumentException("Un email valide est obligatoire.");
        }
        return employeeService.createEmployee(employee);
    }

    // Modifier un employé
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        // Ajout d'une validation manuelle si nécessaire
        if (updatedEmployee.getName() == null || updatedEmployee.getName().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'employé est obligatoire.");
        }
        if (updatedEmployee.getEmail() == null || !updatedEmployee.getEmail().contains("@")) {
            throw new IllegalArgumentException("Un email valide est obligatoire.");
        }

        try {
            return ResponseEntity.ok(employeeService.updateEmployee(id, updatedEmployee));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un employé
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Rechercher des employés par nom ou email
    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String query) {
        return employeeService.searchEmployees(query);
    }
}
