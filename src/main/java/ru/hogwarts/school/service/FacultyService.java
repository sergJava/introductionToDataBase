package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FacultyService {
    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        checkId(id);
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        checkId(id);
        facultyRepository.deleteById(id);
    }

    private void checkId(long id) {
        if (!facultyRepository.existsById(id)) {
            throw new IllegalArgumentException("нет факультета с таким id: " + id);
        }
    }

    public List<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> findByNameOrColor(String name, String color){
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public List<Student> findStudentsByFacultyId(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudents)
                .orElseThrow(() -> new IllegalArgumentException("нет такого факультета"));
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public String findLongestName(){
        String longestName = facultyRepository.findAll().stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparingInt(String::length))
                .orElse("Факультета нет");
        return longestName;
    }

    public Integer getCountWithParallelStream(){
        long startTime = System.nanoTime();
        int sum = Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
        long endTime = System.nanoTime();
        long timing = (endTime - startTime)/1_000_000;
        System.out.println("затрачено " + timing + " мсек.");
        return sum;
    }
}
