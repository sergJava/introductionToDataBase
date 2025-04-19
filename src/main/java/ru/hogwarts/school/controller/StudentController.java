package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping        //POST http://localhost:8080/student
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping     //GET http://localhost:8080/student?id=3
    public ResponseEntity<Student> getStudent(@RequestParam long id) {
        if (id < 1) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.getStudent(id));
    }

//    @PutMapping("/add/{name}/{age}")        //POST http://localhost:8080/student/add/vovva/43
//    public Student addStudent(@PathVariable String name, @PathVariable int age) {
//        return studentService.add(name, age);
//    }

    @PutMapping     //PUT http://localhost:8080/student
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("{id}")      //DELETE http://localhost:8080/student/5
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @GetMapping("/getAll")     //GET http://localhost:8080/student/getAll
    public ResponseEntity<Collection<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/sorting")     //GET http://localhost:8080/student/sorting?age=41
    public Collection<Student> sortingByAge(@RequestParam int age) {
        return studentService.sortingByAge(age);
    }
}
