package ru.hogwarts.school.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerWithMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    FacultyController facultyController;

    @Test
    public void createFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Test Name1";
        String color = "color1";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("color", color);
        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$id").value(id))
                .andExpect(jsonPath("$name").value(name))
                .andExpect(jsonPath("$color").value(color));
    }

    @Test
    public void getFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Test Name2";
        String color = "color2";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("color", color);
        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?id=" + faculty.getId())
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$id").value(id))
                .andExpect(jsonPath("$name").value(name))
                .andExpect(jsonPath("$color").value(color));
    }
}
