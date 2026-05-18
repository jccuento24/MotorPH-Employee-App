/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cp2.service;

import cp2.model.Employee;
import cp2.model.AttendanceRecord;
import java.util.List;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

/**
 *
 * @author crbnl
 */

public class Payroll {
    
    private Deduction deduction = new Deduction();
    
    private static final LocalTime WORKDAY_START = LocalTime.of(8, 0);
    private static final LocalTime WORKDAY_END = LocalTime.of(17, 0);
    private static final LocalTime GRACE_LIMIT = LocalTime.of(8, 5);

    public void processPayroll(Employee employee, List<AttendanceRecord> records) {
        
        double hourlyRate = employee.getHourlyRate();
        double basicSalary = employee.getBasicSalary();

        for (int month = 6; month <= 12; month++) {
            YearMonth ym = YearMonth.of(LocalDate.now().getYear(), month);

            LocalDate firstCutoffStart = ym.atDay(1);
            LocalDate firstCutoffEnd = ym.atDay(15);
            LocalDate secondCutoffStart = ym.atDay(16);
            LocalDate secondCutoffEnd = ym.atEndOfMonth();

            // Hours worked per cutoff
            double firstHours = computeTotalHours(records, employee.getEmployeeNumber(), firstCutoffStart, firstCutoffEnd);
            double secondHours = computeTotalHours(records, employee.getEmployeeNumber(), secondCutoffStart, secondCutoffEnd);

            // Gross pay
            double firstGross = firstHours * hourlyRate;
            double secondGross = secondHours * hourlyRate;
            double gross = firstGross + secondGross;

            // Deductions
            double sss = deduction.computeSSS(basicSalary);
            double philHealth = deduction.computePhilHealth(basicSalary);
            double pagIbig = deduction.computePagIbig(basicSalary);
            double tax = deduction.computeIncomeTax(basicSalary * 12) / 12;

            double totalDeductions = sss + philHealth + pagIbig + tax;

            // Net pay
            double firstNet = firstGross; // no deductions
            double secondNet = secondGross - totalDeductions;
            double monthlyNet = firstNet + secondNet;

            // Print payroll summary
            System.out.println("======================================");
            System.out.println("Payroll for " + ym.getMonth());
            System.out.println("Employee: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.println("Position: " + employee.getPosition());
            System.out.println("Supervisor: " + employee.getSupervisor());
            System.out.println("Basic Salary: " + basicSalary);
            System.out.println("Gross (Month): " + gross);
            System.out.println("First Cutoff Gross: " + firstGross + " | Net: " + firstNet);
            System.out.println("Second Cutoff Gross: " + secondGross + " | Net: " + secondNet);
            System.out.println("Deductions: SSS=" + sss + ", PhilHealth=" + philHealth +
                               ", Pag-IBIG=" + pagIbig + ", Tax=" + tax);
            System.out.println("Total Deductions: " + totalDeductions);
            System.out.println("Net Salary (Month): " + monthlyNet);
            System.out.println("======================================\n");
        }
    }
    
     private double computeTotalHours(List<AttendanceRecord> records, int empNo, LocalDate start, LocalDate end) {
        return records.stream()
                .filter(r -> r.getEmployeeNumber() == empNo &&
                             !r.getDate().isBefore(start) &&
                             !r.getDate().isAfter(end))
                .mapToDouble(r -> computeDailyHours(r.getTimeIn(), r.getTimeOut()))
                .sum();
    }

    /**
     * Compute valid worked hours for one attendance record.
    */
     
    private double computeDailyHours(LocalTime timeIn, LocalTime timeOut) {
        if (timeIn == null || timeOut == null) return 0.0;

        LocalTime adjustedStart = (!timeIn.isAfter(GRACE_LIMIT)) ? WORKDAY_START : timeIn;
        LocalTime adjustedEnd = timeOut.isAfter(WORKDAY_END) ? WORKDAY_END : timeOut;

        if (adjustedEnd.isBefore(adjustedStart)) return 0.0;

        long minutes = Duration.between(adjustedStart, adjustedEnd).toMinutes();
        return minutes / 60.0;
    }
}
    
