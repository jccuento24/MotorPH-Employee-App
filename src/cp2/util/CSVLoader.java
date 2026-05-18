/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cp2.util;

import cp2.model.Employee;
import cp2.model.AttendanceRecord;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author crbnl
 */

public class CSVLoader {
    
    public List<Employee> loadEmployees(String filePath) throws IOException {
        
        List<Employee> employees = new ArrayList<>();
    DateTimeFormatter birthdayFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        br.readLine(); // skip header
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 18) {
                System.out.println("Skipping invalid row: " + line);
                continue;
            }

            employees.add(new Employee(
                Integer.parseInt(parts[0]),              // employee number
                parts[1],                               // last name
                parts[2],                               // first name
                LocalDate.parse(parts[3], birthdayFormatter).toString(),
                parts[4],                               // address
                parts[5],                               // phone
                parts[6],                               // sss
                parts[7],                               // philhealth
                parts[8],                               // tin
                parts[9],                               // pagibig
                parts[10],                              // status
                parts[11],                              // position
                parts[12],                              // supervisor
                parseDoubleSafe(parts[13]),             // basic salary
                parseDoubleSafe(parts[14]),             // rice subsidy
                parseDoubleSafe(parts[15]),             // phone allowance
                parseDoubleSafe(parts[16]),             // gross semi-monthly
                parseDoubleSafe(parts[17])              // hourly rate
            ));
        }
    }
    return employees;
    
    }

    public List<AttendanceRecord> loadAttendance(String filePath) throws IOException {
        List<AttendanceRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                records.add(new AttendanceRecord(
                    Integer.parseInt(parts[0]),
                    LocalDate.parse(parts[1]),
                    LocalTime.parse(parts[2]),
                    LocalTime.parse(parts[3])
                ));
            }
        }
        return records;
    }
    
    private double parseDoubleSafe(String value) {
    if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("N/A")) {
        return 0.0;
    }
    try {
        return Double.parseDouble(value.trim());
    } catch (NumberFormatException e) {
        return 0.0;
    }
}
}
