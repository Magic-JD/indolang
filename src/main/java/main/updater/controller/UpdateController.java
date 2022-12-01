package main.updater.controller;

import main.updater.data.Definition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class UpdateController {

    @PostMapping("/user")
    public ResponseEntity<Boolean> addWordToDictionary(@RequestBody Definition definition) {
        return ResponseEntity.ok(true);
    }

}
