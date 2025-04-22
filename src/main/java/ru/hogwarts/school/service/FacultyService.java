package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        checkId(id);
        return repository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public void removeFaculty(Long id) {
        checkId(id);
        repository.deleteById(id);
    }

    private void checkId(long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("нет факультета с таким id: " + id);
        }
    }

    public Collection<Faculty> findByColor(String color) {
        return repository.findByColor(color);
    }
}
