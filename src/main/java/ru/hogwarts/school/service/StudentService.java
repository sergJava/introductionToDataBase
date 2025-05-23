package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        checkId(id);
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        Student studentFromDb = getStudent(student.getId());
        studentFromDb.setName(student.getName());
        studentFromDb.setAge(student.getAge());
        if (student.getFaculty() != null) {
            studentFromDb.setFaculty(student.getFaculty());
        }
        return studentRepository.save(studentFromDb);
    }

    public void deleteStudent(Long id) {
        checkId(id);
        studentRepository.deleteById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    private void checkId(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("нет студента с таким id");
        }
    }

    public List<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudentId(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Faculty faculty = student.getFaculty();
            if (faculty != null) {
                return faculty;
            }
        }
        throw new IllegalArgumentException("нет такого студента");
    }

    public Integer getCountOfStudents() {
        return studentRepository.getCountOfStudents();
    }

    public Double getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public List<Student> findLastFiveStudent() {
        return studentRepository.findLastFiveStudent();
    }


}
