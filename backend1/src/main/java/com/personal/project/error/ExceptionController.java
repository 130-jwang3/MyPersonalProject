package com.personal.project.error;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExceptionController {
    @GetMapping("/illegalArgumentException")
    public void throwException() {
        throw new IllegalArgumentException();
    }

    @GetMapping("/resourceNotFoundException")
    public void throwException2() {
        throw new ResourceNotFoundException();
    }
}
