package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(service.createFaculty(faculty));
    }

    @GetMapping
    public ResponseEntity<Faculty> getFaculty(@RequestParam Long id) {
        return ResponseEntity.ok(service.getFaculty(id));
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(service.updateFaculty(faculty));
    }

    @DeleteMapping
    public ResponseEntity<Faculty> deleteFaculty(@RequestParam Long id) {
        service.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-color")
    public Collection<Faculty> findByColor(@RequestParam String color) {
        return service.findByColor(color);
    }

    @GetMapping("/by-name-or-color")
    public Collection<Faculty> findByNameIgnoreCaseOrByColorIgnoreCase(@RequestParam String name, @RequestParam String color){
        return service.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}
