package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping
    public ResponseEntity<Faculty> getFaculty(@RequestParam long id) {
        return ResponseEntity.ok(facultyService.getFaculty(id));
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestParam long id, @RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.updateFaculty(id, faculty));
    }

    @DeleteMapping
    public ResponseEntity<Faculty> deleteFaculty(@RequestParam long id) {
        return ResponseEntity.ok(facultyService.removeFaculty(id));
    }

    @GetMapping("/sortingByColor")
    public Collection<Faculty> sortingByColor(@RequestParam String color) {
        return facultyService.sortingByColor(color);
    }
}
