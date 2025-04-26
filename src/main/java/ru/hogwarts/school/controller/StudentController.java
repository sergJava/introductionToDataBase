package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping
    public ResponseEntity<Student> getStudent(@RequestParam long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<Collection<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/by-age")
    public Collection<Student> findByAge(@RequestParam int age) {
        return studentService.findByAge(age);
    }

    @GetMapping("/by-age-between")
    public Collection<Student> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getFacultyByStudentId(id));
    }
}
