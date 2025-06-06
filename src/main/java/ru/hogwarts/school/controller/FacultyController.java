package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

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
    public ResponseEntity<Faculty> getFaculty(@RequestParam Long id) {
        return ResponseEntity.ok(facultyService.getFaculty(id));
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.updateFaculty(faculty));
    }

    @DeleteMapping
    public ResponseEntity<Faculty> deleteFaculty(@RequestParam Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-color")
    public Collection<Faculty> findByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping("/by-name-or-color")
    public Collection<Faculty> findByNameOrColor(@RequestParam String name, @RequestParam String color) {
        return facultyService.findByNameOrColor(name, color);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> findStudentsByFacultyId(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findStudentsByFacultyId(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }
}
