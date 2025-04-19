package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private static long id = 1;

    public Student createStudent(Student student) {
        student.setId(id);
        students.put(id, student);
        id++;
        return student;
    }

//    public Student add(String name, int age) {
//        return createStudent(new Student(0, name, age));
//    }

    public Student getStudent(long id) {
        checkId(id);
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        return students.put(student.getId(), student);
    }

    public Student deleteStudent(long id) {
        checkId(id);
        return students.remove(id);
    }

    private void checkId(long id) {
        if (!students.containsKey(id)) {
            throw new IllegalArgumentException("нет студента с таким id");
        }
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Collection<Student> sortingByAge(int age) {
        return students.values()
                .stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }
}
