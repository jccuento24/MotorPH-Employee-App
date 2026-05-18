/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cp2;

import cp2.ui.LoginMenu;
import cp2.ui.PayrollMenu;
import cp2.ui.EmployeeMenu;
import cp2.service.Payroll;
import cp2.util.CSVLoader;
import cp2.model.Employee;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author crbnl
 */

public class CP2 {

    
    public static void main(String[] args) {
        
       Scanner scanner = new Scanner(System.in);
        LoginMenu login = new LoginMenu();
        String role = login.authenticateUser(scanner);

        if (role == null) {
            System.out.println("Incorrect credentials. Terminating program.");
            return;
        }

        try {
            CSVLoader loader = new CSVLoader();
            List<Employee> employees = loader.loadEmployees("resources/MotorPH_Employee_Data_Employee_Details.csv");
            Payroll payrollService = new Payroll();

            if ("employee".equals(role)) {
                new EmployeeMenu(employees, payrollService).showMenu(scanner);
            } else if ("payroll_staff".equals(role)) {
                new PayrollMenu(employees, payrollService).showMenu(scanner);
            }
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    
}
