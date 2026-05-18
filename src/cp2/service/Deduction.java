/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cp2.service;

/**
 *
 * @author crbnl
 */
public class Deduction {
    
    public double computeSSS(double monthlySalary) {
        double msc = Math.min(Math.max(monthlySalary, 5000), 35000);
        return msc * 0.05;
    }

    public double computePhilHealth(double monthlySalary) {
        double premium = monthlySalary * 0.05;
        premium = Math.max(500, Math.min(premium, 5000));
        return premium / 2;
    }

    public double computePagIbig(double monthlySalary) {
        return Math.min(monthlySalary * 0.02, 100.0);
    }

    public double computeIncomeTax(double annualIncome) {
        if (annualIncome <= 250000) return 0.0;
        else if (annualIncome <= 400000) return (annualIncome - 250000) * 0.20;
        else if (annualIncome <= 800000) return 30000 + (annualIncome - 400000) * 0.25;
        else if (annualIncome <= 2000000) return 130000 + (annualIncome - 800000) * 0.30;
        else return 490000 + (annualIncome - 2000000) * 0.35;
    }
    
}
