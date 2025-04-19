package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static long id = 1;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(id);
        faculties.put(id, faculty);
        id++;
        return faculty;
    }

    public Faculty getFaculty(long id) {
        checkId(id);
        return faculties.get(id);
    }

    public Faculty updateFaculty(long id, Faculty faculty) {
        checkId(id);
        faculty.setId(id);
        return faculties.put(id, faculty);
    }

    public Faculty removeFaculty(long id) {
        checkId(id);
        return faculties.remove(id);
    }

    private void checkId(long id) {
        if (!faculties.containsKey(id)) {
            throw new IllegalArgumentException("нет факультета с таким id: " + id);
        }
    }

    public Collection<Faculty> sortingByColor(String color) {
        return faculties.values()
                .stream()
                .filter(item -> item.getColor().equals(color))
                .toList();
    }
}
