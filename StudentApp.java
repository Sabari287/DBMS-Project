package com.example.sms.app;

import com.example.sms.models.Student;
import com.example.sms.dao.StudentDAO;
import java.util.*;
import java.sql.SQLException;

public class StudentApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentDAO dao;

        try {
            dao = new StudentDAO();
        } catch (SQLException e) {
            System.out.println("❌ Cannot connect to database.");
            e.printStackTrace();
            return;
        }

        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. View Students by Department");
            System.out.println("2. Manage Students");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int mainChoice = sc.nextInt();
            sc.nextLine();

            try {
                switch (mainChoice) {
                    case 1:
                        System.out.print("Enter Department Name: ");
                        String deptName = sc.nextLine();
                        dao.viewByDepartment(deptName);
                        break;

                    case 2:
                        while (true) {
                            System.out.println("\n=== Manage Students ===");
                            System.out.println("1. Add Student");
                            System.out.println("2. Update Student Details");
                            System.out.println("3. Delete Student");
                            System.out.println("4. Back to Main Menu");
                            System.out.print("Choose option: ");
                            int subChoice = sc.nextInt();
                            sc.nextLine();

                            switch (subChoice) {
                                case 1: // Add Student
                                    System.out.print("Enter Roll No: ");
                                    int roll = sc.nextInt(); sc.nextLine();
                                    System.out.print("Enter Name: ");
                                    String name = sc.nextLine();
                                    System.out.print("Enter Email: ");
                                    String email = sc.nextLine();
                                    System.out.print("Enter Dept ID: ");
                                    int dept = sc.nextInt(); sc.nextLine();

                                    if (!dao.isDepartmentExist(dept)) {
                                        System.out.println("❌ Department not found!");
                                        break;
                                    }

                                    Double[] gpas = new Double[8];
                                    for (int i = 0; i < 8; i++) {
                                        System.out.print("Enter GPA for Sem " + (i + 1) + " (or press Enter to skip): ");
                                        String input = sc.nextLine();
                                        gpas[i] = input.isEmpty() ? null : Double.parseDouble(input);
                                    }

                                    Student s = new Student(roll, name, email, dept,
                                            gpas[0], gpas[1], gpas[2], gpas[3],
                                            gpas[4], gpas[5], gpas[6], gpas[7]);
                                    dao.addStudent(s);
                                    break;

                                case 2: // Update Student
                                    System.out.print("Enter Roll No to Update: ");
                                    int updateRoll = sc.nextInt(); sc.nextLine();

                                    if (!dao.isStudentExist(updateRoll)) {
                                        System.out.println("❌ Student not found!");
                                        break;
                                    }

                                    while (true) {
                                        System.out.println("\n=== Update Student Details ===");
                                        System.out.println("1. Update Name");
                                        System.out.println("2. Update Email");
                                        System.out.println("3. Update Department");
                                        System.out.println("4. Update GPA");
                                        System.out.println("5. Back");
                                        System.out.print("Choose option: ");
                                        int updateChoice = sc.nextInt(); sc.nextLine();

                                        switch (updateChoice) {
                                            case 1:
                                                System.out.print("Enter New Name: ");
                                                String newName = sc.nextLine();
                                                dao.updateName(updateRoll, newName);
                                                break;

                                            case 2:
                                                System.out.print("Enter New Email: ");
                                                String newEmail = sc.nextLine();
                                                dao.updateEmail(updateRoll, newEmail);
                                                break;

                                            case 3:
                                                System.out.print("Enter New Dept ID: ");
                                                int newDept = sc.nextInt(); sc.nextLine();
                                                if (!dao.isDepartmentExist(newDept)) {
                                                    System.out.println("❌ Department not found!");
                                                    break;
                                                }
                                                dao.updateDepartment(updateRoll, newDept);
                                                break;

                                            case 4:
                                                System.out.print("Enter Semester (1–8): ");
                                                int sem = sc.nextInt();
                                                System.out.print("Enter New GPA: ");
                                                double newGpa = sc.nextDouble(); sc.nextLine();
                                                dao.updateGPA(updateRoll, sem, newGpa);
                                                break;

                                            case 5:
                                                System.out.println("Returning to Manage Students menu...");
                                                break;

                                            default:
                                                System.out.println("Invalid choice!");
                                        }

                                        if (updateChoice == 5)
                                            break;
                                    }
                                    break;

                                case 3: // Delete Student
                                    System.out.print("Enter Roll No to Delete: ");
                                    int rollDel = sc.nextInt(); sc.nextLine();
                                    dao.deleteStudent(rollDel);
                                    break;

                                case 4:
                                    System.out.println("Returning to Main Menu...");
                                    break;

                                default:
                                    System.out.println("Invalid choice!");
                            }

                            if (subChoice == 4)
                                break;
                        }
                        break;

                    case 3:
                        System.out.println("Exiting... Bye!");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (SQLException e) {
                System.out.println("❌ Database error occurred:");
                e.printStackTrace();
            }
        }
    }
}