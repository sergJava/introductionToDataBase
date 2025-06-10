package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        logger.debug("student {} created", student);
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        logger.info("Was invoked method for get student");
        checkId(id);
        Student student = studentRepository.findById(id).get();
        logger.debug("get student {}", student);
        return student;
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoke method for update student");
        Student studentFromDb = getStudent(student.getId());
        studentFromDb.setName(student.getName());
        studentFromDb.setAge(student.getAge());
        if (student.getFaculty() != null) {
            studentFromDb.setFaculty(student.getFaculty());
        }
        return studentRepository.save(studentFromDb);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoke method for delete student");
        checkId(id);
        studentRepository.deleteById(id);
    }

    public List<Student> getAllStudents() {
        logger.info("Was invoke method for get all students");
        return studentRepository.findAll();
    }

    private void checkId(Long id) {
        logger.info("Was invoked method checkId");
        if (!studentRepository.existsById(id)) {
            logger.error("There is not student with id = {}", id);
            throw new IllegalArgumentException("нет студента с таким id");
        }
    }

    public List<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoke method for get student by age");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudentId(Long studentId) {
        logger.info("Was invoke method getFacultyByStudentId");
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Faculty faculty = student.getFaculty();
            if (faculty != null) {
                logger.debug("get faculty {}", faculty);
                return faculty;
            }
        }
        logger.error("There is not student with id = {}", studentId);
        throw new IllegalArgumentException("нет такого студента");
    }

    public Integer getCountOfStudents() {
        logger.info("Was invoke method getCountOfStudents");
        Integer countOfStudents = studentRepository.getCountOfStudents();
        logger.debug("count of student is {}", countOfStudents);
        return countOfStudents;
    }

    public Double getAverageAge() {
        logger.info("Was invoke method getAverageAge");
        Double averageAge = studentRepository.getAverageAge();
        logger.debug("average age is {}", averageAge);
        return averageAge;
    }

    public List<Student> findLastFiveStudent() {
        logger.info("Was invoke method findLastFiveStudent");
        logger.warn("warning! was invoke method findLastFiveStudents");
        return studentRepository.findLastFiveStudent();
    }

    public List<String> findStudentNamesBeginWithA() {
        List<String> names = studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.toUpperCase().startsWith("A"))
                .sorted()
                .map(String::toUpperCase)
                .toList();
        return names;
    }

    public Double getAverageAgeByStream() {
        Double average = studentRepository.findAll().stream()
                .mapToInt(student -> student.getAge())
                .average()
                .orElse(0);
        return average;
    }

}
