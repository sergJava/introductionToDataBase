package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;


    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Value("${student.avatar.dir.path}")
    private String avatarsDir;

    public Avatar createAvatar(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    public Avatar updateAvatar(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    public Avatar getAvatar(Long id) {
        checkId(id);
        return avatarRepository.findById(id).get();
    }

    public void deleteAvatar(Long id) {
        checkId(id);
        avatarRepository.deleteById(id);
    }

    private void checkId(Long id) {
        if (!avatarRepository.existsById(id)) {
            throw new IllegalArgumentException("нет аватара с таким id");
        }
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentService.getStudent(studentId);
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream((os), 1024)) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getAvatars(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
