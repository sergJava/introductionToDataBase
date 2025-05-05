package ru.hogwarts.school.controller;

import org.json.JSONObject;
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
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FacultyRepository facultyRepository;

    private String baseUrl;

//    private Student student;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/faculty";

        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void createFacultyTest() {
        Faculty faculty = new Faculty();
        String name = "Faculty Test1";
        String color = "color1";
        faculty.setName(name);
        faculty.setColor(color);

        ResponseEntity<Faculty> response = restTemplate.postForEntity(baseUrl, faculty, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(name);
    }

    @Test
    void getStudentTest() throws Exception{
        Faculty faculty = new Faculty();
        String name = "Faculty Test2";
        String color = "color2";
        faculty.setName(name);
        faculty.setColor(color);
        Faculty savedFaculty = facultyRepository.save(faculty);

        String url = baseUrl + "?id=" + savedFaculty.getId();
        ResponseEntity<Faculty> response = restTemplate.getForEntity(url, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getColor()).isEqualTo(color);
    }

    @Test
    void updateFacultyTest() {
        Faculty faculty = new Faculty("Old Faculty", "old color");
        Faculty savedFaculty = facultyRepository.save(faculty);

        String newName = "New Faculty";
        String newColor = "new color";
        savedFaculty.setName(newName);
        savedFaculty.setColor(newColor);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Faculty> request = new HttpEntity<>(savedFaculty, headers);

        ResponseEntity<Faculty> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo(newName);
        assertThat(response.getBody().getColor()).isEqualTo(newColor);
    }

    @Test
    void deleteFacultyTest() {
        Faculty faculty = new Faculty("Faculty Test4", "color4");
        Faculty savedFaculty = facultyRepository.save(faculty);

        String url = baseUrl + "?id=" + savedFaculty.getId();
        ResponseEntity<Faculty> response = restTemplate.getForEntity(url, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        restTemplate.delete(url);

        assertThat(facultyRepository.findById(savedFaculty.getId()).isEmpty());
    }

    @Test
    void findByColorTest() {
        String name = "Faculty Test5";
        String correctColor = "correct color";
        Faculty faculty1 = new Faculty("Faculty Test5", correctColor);
        Faculty faculty2 = new Faculty("Faculty Test6", correctColor);
        Faculty faculty3 = new Faculty("Faculty Test7", "incorrect color");

        facultyRepository.saveAll(List.of(faculty1, faculty2, faculty3));

        String url = baseUrl + "/by-color?color=" + correctColor;

        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(url, Faculty[].class);

        Faculty[] faculties = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(faculties).isNotNull();
        assertThat(faculties).hasSize(2);
        for (Faculty faculty : faculties) {
            assertThat(faculty.getColor()).isEqualTo(correctColor);
        }
    }

    @Test
    void findStudentsByFacultyIdTest() {
        Faculty faculty = facultyRepository.save(new Faculty("Test6", "color6"));
        Student student1 = new Student(20, "Ivan");
        Student student2 = new Student(21, "Anna");
        student1.setFaculty(faculty);
        student2.setFaculty(faculty);
        studentRepository.save(student1);
        studentRepository.save(student2);

        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                baseUrl + "/" + faculty.getId() + "/students",
                Student[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }
}
