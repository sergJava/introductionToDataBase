package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.AvatarService;

@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }
}
