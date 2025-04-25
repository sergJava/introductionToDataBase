package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;


@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Student getStudent(Long id) {
        checkId(id);
        return repository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        return repository.save(student);
    }

    public void deleteStudent(Long id) {
        checkId(id);
        repository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }

    private void checkId(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("нет студента с таким id");
        }
    }

    public Collection<Student> findByAge(int age) {
        return repository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return repository.findByAgeBetween(min, max);
    }
}
