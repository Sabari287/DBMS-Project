-- Create Department Table

CREATE TABLE departments (
                             dept_id NUMBER PRIMARY KEY,
                             dept_name VARCHAR2(100) NOT NULL
);

-- Create Student Table

CREATE TABLE students (
                          roll_no NUMBER PRIMARY KEY,
                          name VARCHAR2(100) NOT NULL,
                          email VARCHAR2(100),
                          dept_id NUMBER,
                          sem1_gpa NUMBER(4,2),
                          sem2_gpa NUMBER(4,2),
                          sem3_gpa NUMBER(4,2),
                          sem4_gpa NUMBER(4,2),
                          sem5_gpa NUMBER(4,2),
                          sem6_gpa NUMBER(4,2),
                          sem7_gpa NUMBER(4,2),
                          sem8_gpa NUMBER(4,2),
                          CONSTRAINT fk_dept FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);

-- Insert Sample Departments
INSERT INTO departments VALUES (1, 'CSE');
INSERT INTO departments VALUES (2, 'IT');
INSERT INTO departments VALUES (3, 'AIML');
INSERT INTO departments VALUES (4, 'MECH');
INSERT INTO departments VALUES (5, 'EEE');
INSERT INTO departments VALUES (6, 'ECE');
INSERT INTO departments VALUES (7, 'Chemical Engineering');
INSERT INTO departments VALUES (8, 'Food Technology');
INSERT INTO departments VALUES (9, 'AIDS');

COMMIT;

