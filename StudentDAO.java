package com.example.sms.dao;

import com.example.sms.models.Student;
import com.example.sms.util.DBConnection;
import java.sql.*;

public class StudentDAO {
    private Connection conn;

    public StudentDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public boolean isDepartmentExist(int deptId) throws SQLException {
        String query = "SELECT COUNT(*) FROM departments WHERE dept_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, deptId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    public boolean isStudentExist(int roll) throws SQLException {
        String sql = "SELECT * FROM students WHERE roll_no = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, roll);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public void addStudent(Student s) throws SQLException {
        String sql = "INSERT INTO students VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, s.getRollNumber());
        ps.setString(2, s.getName());
        ps.setString(3, s.getEmail());
        ps.setInt(4, s.getDeptId());
        Double[] gpas = s.getGpas();
        for (int i = 0; i < 8; i++) {
            if (gpas[i] == null) ps.setNull(5 + i, Types.DOUBLE);
            else ps.setDouble(5 + i, gpas[i]);
        }
        ps.executeUpdate();
        System.out.println("✅ Student added successfully!");
    }

    public void viewByDepartment(String deptName) throws SQLException {
        String sqlDept = "SELECT dept_id, dept_name FROM departments WHERE LOWER(dept_name) = LOWER(?)";
        PreparedStatement psDept = conn.prepareStatement(sqlDept);
        psDept.setString(1, deptName);
        ResultSet rsDept = psDept.executeQuery();

        if (!rsDept.next()) {
            System.out.println("❌ Department '" + deptName + "' does not exist.");
            return;
        }

        int deptId = rsDept.getInt("dept_id");
        String actualDeptName = rsDept.getString("dept_name");

        String sql = """
            SELECT s.roll_no, s.name, s.email, d.dept_name,
                   s.sem1_gpa, s.sem2_gpa, s.sem3_gpa, s.sem4_gpa,
                   s.sem5_gpa, s.sem6_gpa, s.sem7_gpa, s.sem8_gpa
            FROM students s
            JOIN departments d ON s.dept_id = d.dept_id
            WHERE s.dept_id = ?
            ORDER BY s.roll_no
        """;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, deptId);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n=== Students in " + actualDeptName.toUpperCase() + " Department ===");
        boolean found = false;
        while (rs.next()) {
            found = true;
            int roll = rs.getInt("roll_no");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String dept = rs.getString("dept_name");

            String g1 = formatGPA(rs.getObject("sem1_gpa"));
            String g2 = formatGPA(rs.getObject("sem2_gpa"));
            String g3 = formatGPA(rs.getObject("sem3_gpa"));
            String g4 = formatGPA(rs.getObject("sem4_gpa"));
            String g5 = formatGPA(rs.getObject("sem5_gpa"));
            String g6 = formatGPA(rs.getObject("sem6_gpa"));
            String g7 = formatGPA(rs.getObject("sem7_gpa"));
            String g8 = formatGPA(rs.getObject("sem8_gpa"));

            System.out.println("\nRoll No : " + roll);
            System.out.println("Name    : " + name);
            System.out.println("Email   : " + email);
            System.out.println("Dept    : " + dept);
            System.out.println("GPA (Sem1–8): " + String.join(", ", g1, g2, g3, g4, g5, g6, g7, g8));
            System.out.println("-------------------------------------------");
        }

        if (!found) System.out.println("No students found in this department.");
    }

    private String formatGPA(Object gpa) { return gpa == null ? "-" : gpa.toString(); }

    public void updateName(int roll, String newName) throws SQLException {
        String sql = "UPDATE students SET name=? WHERE roll_no=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newName);
        ps.setInt(2, roll);
        ps.executeUpdate();
        System.out.println("✅ Name updated successfully!");
    }

    public void updateEmail(int roll, String newEmail) throws SQLException {
        String sql = "UPDATE students SET email=? WHERE roll_no=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newEmail);
        ps.setInt(2, roll);
        ps.executeUpdate();
        System.out.println("✅ Email updated successfully!");
    }

    public void updateDepartment(int roll, int newDept) throws SQLException {
        String sql = "UPDATE students SET dept_id=? WHERE roll_no=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, newDept);
        ps.setInt(2, roll);
        ps.executeUpdate();
        System.out.println("✅ Department updated successfully!");
    }

    public void updateGPA(int roll, int sem, double newGpa) throws SQLException {
        String sql = "UPDATE students SET sem" + sem + "_gpa=? WHERE roll_no=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, newGpa);
        ps.setInt(2, roll);
        ps.executeUpdate();
        System.out.println("✅ GPA updated successfully!");
    }

    public void deleteStudent(int roll) throws SQLException {
        String sql = "DELETE FROM students WHERE roll_no=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, roll);
        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("🗑 Student deleted successfully!");
        else System.out.println("❌ Student not found!");
    }
}