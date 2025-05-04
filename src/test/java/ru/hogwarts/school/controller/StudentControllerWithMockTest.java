package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
//@WebMvcTest
public class StudentControllerWithMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void createStudentTest() throws Exception {
        Long id = 1L;
        int age = 51;
        String name = "Test Name1";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("age", age);
        jsonObject.put("name", name);
        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
//        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void getStudentTest() throws Exception {
        Long id = 2L;
        int age = 52;
        String name = "Test Name2";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("age", age);
        jsonObject.put("name", name);
        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);

//        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentRepository.existsById(any(Long.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?id=" + student.getId())
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void putStudentTest() throws Exception {
        Long id = 3L;
        int age = 53;
        String name = "New Name";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("age", age);
        jsonObject.put("name", name);
        Student existsStudent = new Student(49, "Old Name");
        existsStudent.setId(3L);
        Student updatedStudent = new Student();
        updatedStudent.setId(id);
        updatedStudent.setAge(age);
        updatedStudent.setName(name);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(existsStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);
        when(studentRepository.existsById(any(Long.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Long id = 4L;
        int age = 54;
        String name = "Test Name4";

        when(studentRepository.existsById(any(Long.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id))
                .andExpect(status().isOk());

        Mockito.verify(studentRepository).deleteById(any(Long.class));
    }


}

