CREATE TABLE student_data (id INT NOT NULL Primary Key AUTO_INCREMENT, studentName VARCHAR(30), studentId VARCHAR(20), exercises INT, assignment1 INT, assignment2 INT, assignment3 INT, midterm INT, finalExam INT, weightedAverage DECIMAL(5,2), letterGrade VARCHAR(5));

INSERT INTO student_data (studentName, studentId, exercises, assignment1, assignment2, assignment3, midterm, finalExam, weightedAverage, letterGrade) values
('peter', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+'),
('petera', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+'),
('peterb', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+'),
('peterc', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+'),
('peterd', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+'),
('petere', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+'),
('peterf', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+'),
('peterh', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+'),
('peteri', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+'),
('peterg', '123', 80, 90, 100, 90, 90, 95, 91.70, 'A+');
