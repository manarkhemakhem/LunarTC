package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    private EmployeeRepository employeeRepository; // Injection du repository pour accéder aux données

    // Méthode pour exporter les employés en fichier Excel
    public ByteArrayInputStream exportToExcel() throws IOException {
        // Créez le fichier Excel (utilisez Apache POI)
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");

        // Ajouter les en-têtes et les données des employés
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Poste");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Date d'embauche");

        // Ajoutez des lignes pour les employés
        List<Employee> employees = getEmployeesFromDatabase(); // Récupérez les employés depuis la base de données
        int rowNum = 1;
        for (Employee employee : employees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getPosition());
            row.createCell(3).setCellValue(employee.getEmail());
            row.createCell(4).setCellValue(employee.getHireDate().toString());
        }

        // Convertir le workbook en ByteArrayInputStream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }

    // Cette méthode récupère tous les employés depuis la base de données
    private List<Employee> getEmployeesFromDatabase() {
        // Utiliser le repository pour récupérer tous les employés
        return employeeRepository.findAll();
    }
}
