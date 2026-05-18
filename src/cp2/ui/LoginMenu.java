/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cp2.ui;

import java.util.Scanner;

/**
 *
 * @author crbnl
 */

public class LoginMenu {
    
    private static final String EMPLOYEE_USERNAME = "employee";
    private static final String PAYROLL_USERNAME = "payroll_staff";
    private static final String SYSTEM_PASSWORD = "12345";

    public String authenticateUser(Scanner scanner) {
        
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if ((username.equals(EMPLOYEE_USERNAME) || username.equals(PAYROLL_USERNAME))
                && password.equals(SYSTEM_PASSWORD)) {
            return username;
        }
        return null;
    }
    
}
