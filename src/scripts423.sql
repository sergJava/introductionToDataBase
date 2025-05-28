SELECT s.name, s.age, f.name
FROM student s
INNER JOIN faculty f on s.faculty_id = f.id;

SELECT s.name
FROM student s
RIGHT JOIN avatar a ON s.id = a.student_id;