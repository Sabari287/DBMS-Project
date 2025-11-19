package com.example.sms.models;

public class Student {
    private int rollNumber;
    private String name;
    private String email;
    private int deptId;
    private Double gpaSem1, gpaSem2, gpaSem3, gpaSem4, gpaSem5, gpaSem6, gpaSem7, gpaSem8;

    public Student() {}

    public Student(int rollNumber, String name, String email, int deptId,
                   Double g1, Double g2, Double g3, Double g4,
                   Double g5, Double g6, Double g7, Double g8) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.email = email;
        this.deptId = deptId;
        this.gpaSem1 = g1; this.gpaSem2 = g2; this.gpaSem3 = g3; this.gpaSem4 = g4;
        this.gpaSem5 = g5; this.gpaSem6 = g6; this.gpaSem7 = g7; this.gpaSem8 = g8;
    }

    public int getRollNumber() { return rollNumber; }
    public void setRollNumber(int rollNumber) { this.rollNumber = rollNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }

    public Double[] getGpas() {
        return new Double[]{gpaSem1, gpaSem2, gpaSem3, gpaSem4, gpaSem5, gpaSem6, gpaSem7, gpaSem8};
    }
}