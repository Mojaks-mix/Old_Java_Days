CREATE DATABASE GRADING;

USE GRADING;

-- Create User Table
CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password_hash CHAR(64), -- Storing hashed password
    role VARCHAR(20)
);

-- Insert Dummy Data into User Table
INSERT INTO User (username, password_hash, role)
VALUES ('student1', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'student'),
       ('student2', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'student'),
       ('student3', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'student'),
       ('student4', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'student'),
       ('teacher1', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'teacher'),
       ('teacher2', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'teacher'),
       ('admin1', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'admin');

-- Create Course Table
CREATE TABLE Course (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100)
);

-- Insert Dummy Data into Course Table
INSERT INTO Course (course_name)
VALUES ('Mathematics'),
       ('Physics'),
       ('Chemistry'),
       ('Biology'),
       ('History'),
       ('Literature');

-- Create Grad Table
CREATE TABLE Grad (
    grad_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    teacher_id INT,
    first_mark INT,
    second_mark INT,
    final_mark INT,
    symbol VARCHAR(2),
    FOREIGN KEY (student_id) REFERENCES User(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES User(user_id) ON DELETE SET NULL,
    FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Insert Dummy Data into Grad Table
INSERT INTO Grad(student_id, course_id, teacher_id, first_mark, second_mark, final_mark, symbol)
VALUES (1, 1, 5, 8, 88, 9, 'A'),
       (1, 2, 5, 8, 80, 2, 'B'),
       (1, 3, 5, 2, 91, 4, 'A'),
       (2, 1, 6, 2, 5, 76, 'B'),
       (2, 2, 6, 9, 0, 88, 'A'),
       (2, 3, 6, 0, 2, 85, 'B'),
       (3, 1, 5, 0, 2, 91, 'A'),
       (3, 2, 5, 8, 4, 86, 'B'),
       (3, 3, 5, 9, 3, 5, 'A'),
       (4, 1, 6, 78, 1, 0, 'B'),
       (4, 2, 6, 6, 7, 75, 'C'),
       (4, 3, 6, 1, 8, 82, 'B');
