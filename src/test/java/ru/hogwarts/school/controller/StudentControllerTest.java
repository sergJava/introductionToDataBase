package ru.hogwarts.school.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    private String baseUrl;

    private Faculty faculty;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/student";
//        studentRepository.deleteAll();
//        facultyRepository.deleteAll();

    }

    @Test
    void createStudentTest() {
        faculty = new Faculty();
        faculty.setId(300L);
        faculty.setName("math");
        faculty.setColor("red");
        faculty = facultyRepository.save(faculty);

        Student student = new Student();
        student.setId(200L);
        student.setName("Test student");
        student.setAge(18);
        student.setFaculty(faculty);

        ResponseEntity<Student> response = restTemplate.postForEntity(baseUrl, student, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }
}
