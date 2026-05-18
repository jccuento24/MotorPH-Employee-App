/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cp2.ui;

import cp2.model.Employee;
import cp2.service.Payroll;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author crbnl
 */

public class PayrollMenu {
    
    private List<Employee> employees;
    private Payroll payroll;

    public PayrollMenu(List<Employee> employees, Payroll payroll) {
        this.employees = employees;
        this.payroll = payroll;
    }

    public void showMenu(Scanner scanner) {
        
        while (true) {
            System.out.println("\n=== PAYROLL STAFF MENU ===");
            System.out.println("1. Process One Employee");
            System.out.println("2. Process All Employees");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    System.out.print("Enter employee number: ");
                    int empNo = Integer.parseInt(scanner.nextLine().trim());
                    Employee employee = findEmployee(empNo);
                    if (employee != null) {
                        payroll.processPayroll(employee, List.of()); // placeholder for attendance
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
                case "2" -> {
                    for (Employee e : employees) {
                        payroll.processPayroll(e, List.of()); // placeholder for attendance
                        System.out.println("--------------------------------------------------");
                    }
                }
                case "3" -> {
                    System.out.println("Exiting Payroll Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private Employee findEmployee(int empNo) {
        return employees.stream()
                .filter(e -> e.getEmployeeNumber() == empNo)
                .findFirst()
                .orElse(null);
    }
    
}
