package com.bt.challenge;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    private List<Employee> companyEmployees = new ArrayList<>();

    private int calculateEmployeeSalary(Employee employee) {
        int yearsSinceHiring = LocalDate.now().getYear() - employee.getHiringDate().getYear();
        return employee.getJobTitle().getSalary() * yearsSinceHiring;
    }

    private List<Employee> assignEmployeesToProject(Project project, List<Employee> employees) {
        List<Employee> unavailableEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            boolean isUnavailable = false;
            for (Holiday holiday : employee.getHolidays()) {
                if (!(holiday.getEndingDate().isBefore(project.getStartingDate()) ||
                        holiday.getStartingDate().isAfter(project.getEndingDate()))) {
                    isUnavailable = true;
                    break;
                }
            }
            if (isUnavailable) {
                unavailableEmployees.add(employee);
            } else {
                employee.addProject(project);
            }
        }
        return unavailableEmployees;
    }

    private List<Employee> getDepartmentEmployees(Department department) {
        List<Employee> departmentEmployees = new ArrayList<>();
        for (Employee employee : companyEmployees) {
            if (employee.getDepartments().contains(department)) {
                departmentEmployees.add(employee);
            }
        }
        return departmentEmployees;
    }

    private List<Department> getProjectDepartments(Project project) {
        Set<Department> projectDepartments = new TreeSet<>();
        for (Employee employee : companyEmployees) {
            if (employee.getProjects().contains(project)) {
                projectDepartments.addAll(employee.getDepartments());
            }
        }
        return new ArrayList<>(projectDepartments);
    }

    private void replaceEmployee(Employee leavingEmployee, Employee replacementEmployee) {
        replacementEmployee.setDepartments(leavingEmployee.getDepartments());
        replacementEmployee.setProjects(leavingEmployee.getProjects());
        replacementEmployee.setSuperior(leavingEmployee.getSuperior());

        for (Employee employee : companyEmployees) {
            if (employee.getSuperior() != null && employee.getSuperior().equals(leavingEmployee)) {
                employee.setSuperior(replacementEmployee);
            }
        }

        for (Department department : leavingEmployee.getDepartments()) {
            if (department.getDirector().equals(leavingEmployee)) {
                department.setDirector(replacementEmployee);
            }
        }
    }
}
