package ru.hogwarts.school.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
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

        studentRepository.deleteAll();
        facultyRepository.deleteAll();

        faculty = new Faculty();
        faculty.setName("physics");
        faculty.setColor("green");
        faculty = facultyRepository.save(faculty);
    }

    @Test
    void createStudentTest() {
        Student student = new Student();
        student.setName("Test student1");
        student.setAge(19);
        student.setFaculty(faculty);

        ResponseEntity<Student> response = restTemplate.postForEntity(baseUrl, student, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void getStudentTest() {
        Student student = new Student();
        student.setName("Ivan");
        student.setAge(19);
        student.setFaculty(faculty);
        Student savedStudent = studentRepository.save(student);

        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl + "?id=" + savedStudent.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Ivan");
        assertThat(response.getBody().getAge()).isEqualTo(19);
    }

    @Test
    public void updateStudent() {
        Student student = new Student(23, "Old Student");
        student.setFaculty(faculty);
        Student savedStudent = studentRepository.save(student);

        //изменяем студента
        savedStudent.setName("New Student");
        savedStudent.setAge(24);

        HttpEntity<Student> request = new HttpEntity<>(savedStudent);
        ResponseEntity<Student> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("New Student");
        assertThat(response.getBody().getAge()).isEqualTo(24);
    }

    @Test
    public void deleteStudentTest() {
        Student student = new Student(25, "Student for Delete");
        student.setFaculty(faculty);
        Student savedStudent = studentRepository.save(student);

        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl + "?id=" + savedStudent.getId(), Student.class);
        assertThat(response.getBody().getName()).isEqualTo("Student for Delete");

        restTemplate.delete(baseUrl + "/" + savedStudent.getId());

        assertThat(studentRepository.findById(savedStudent.getId())).isEmpty();
    }

    @Test
    public void getAllTest() {
        Student s1 = new Student();
        s1.setName("Student 1");
        s1.setAge(26);
        s1.setFaculty(faculty);

        Student s2 = new Student();
        s2.setName("Student 2");
        s2.setAge(27);
        s2.setFaculty(faculty);

        studentRepository.saveAll(List.of(s1, s2));

        ResponseEntity<Student[]> response = restTemplate.getForEntity(baseUrl + "/getAll", Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void findByAgeTest() {
        Student student = new Student();
        student.setName("Student for Age");
        student.setAge(28);
        student.setFaculty(faculty);
        studentRepository.save(student);

        ResponseEntity<Student[]> response = restTemplate.getForEntity(baseUrl + "/by-age?age=28", Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getName()).isEqualTo("Student for Age");
    }

    @Test
    void findByAgeBetweenTest() {
        Student s1 = new Student();
        s1.setName("Find by Age Between1");
        s1.setAge(30);
        s1.setFaculty(faculty);

        Student s2 = new Student();
        s2.setName("Find by Age Between1");
        s2.setAge(35);
        s2.setFaculty(faculty);

        studentRepository.saveAll(List.of(s1, s2));

        ResponseEntity<Student[]> response = restTemplate.getForEntity(baseUrl + "/by-age-between?min=29&max=35", Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void getFacultyByStudentIdTest() {
        Student student = new Student();
        student.setName("Get Faculty by Student id");
        student.setAge(37);
        student.setFaculty(faculty);
        student = studentRepository.save(student);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(baseUrl + "/" + student.getId() + "/faculty", Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(faculty.getId());
    }
}

